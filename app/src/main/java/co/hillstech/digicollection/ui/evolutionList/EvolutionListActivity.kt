package co.hillstech.digicollection.ui.evolutionList

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.enums.MonsterLevel
import co.hillstech.digicollection.enums.toInt
import co.hillstech.digicollection.fragments.AlertDialogFragment
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.utils.showBottomSheetDialog
import kotlinx.android.synthetic.main.activity_evolution_list.*
import kotlinx.android.synthetic.main.fragment_alert_dialog.view.*
import kotlinx.android.synthetic.main.view_action_bar.*

class EvolutionListActivity : BaseActivity(), EvolutionListPresenter.View {

    private val presenter = EvolutionListPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evolution_list)

        presenter.view = this

        presenter.requestEvolutionList()

        setupActionBar()
        setupViews()
    }

    override fun setupActionBar() {
        viewActivityTitle.text = getString(R.string.evolution_line)

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }
    }

    override fun showProgressRing() {
        progressRingCall(this)
    }

    override fun hideProgressRing() {
        progressRingDismiss()
    }

    override fun inflateEvolutionList() {
        viewEvolutionList?.run {
            addItemDecoration(EdgeDecorator(16))
            adapter = EvolutionListAdapter(presenter.getEvolutionList(), this@EvolutionListActivity::onEvolutionClick)
            layoutManager = LinearLayoutManager(this@EvolutionListActivity)
        }
    }

    private fun onEvolutionClick(monster: Monster){
        Session.user?.partner?.let {
            val base = (it.type * ((it.type * 1000) - ((it.type * 1000) * 0.5)))
            if(it.experience >= base && it.type < Session.user!!.digivice!!.maxLevel.toInt()){
                showBottomSheetDialog(
                        "Atenção", "Você tem certeza que deseja digivolver seu ${it.species} para um ${monster.species}? Depois de evoluir, o Digimon nunca mais voltará a ser o que era antes.",
                        confirmButtonLabel = getString(R.string.yes),
                        cancelButtonLabel = getString(R.string.no),
                        confirmButtonAction = {
                            presenter.evolveDigimon(it, monster, Session.user!!.id)
                        }
                )
            }else if(it.type >= Session.user!!.digivice!!.maxLevel.toInt()) {
                showBottomSheetDialog(getString(R.string.warning), "O seu Digivice não tem capacidade suficiente para evoluir este Digimon.")
            } else{
                showBottomSheetDialog("Atenção", "${it.species} ainda não tem experiência o suficiente para digivolver. Você precisa acumular mais experiência se quiser fazer seu parceiro evoluir.")
            }
        }
    }

    override fun evolutionMessage(buddy: String, evolution: String, image: String) {
        showBottomSheetDialog(
                title = "Parabéns!",
                message = "O seu ${buddy} digivolveu para um ${evolution} e agora ele está muito mais forte do que antes!",
                image = image,
                confirmButtonLabel = getString(R.string.ok),
                confirmButtonAction = {
                    this@EvolutionListActivity.finish()
                }
        )
    }
}
