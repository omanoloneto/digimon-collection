package co.hillstech.digicollection.ui.digiBank

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.utils.showBottomSheetDialog
import kotlinx.android.synthetic.main.activity_data_box.*
import kotlinx.android.synthetic.main.view_action_bar.*

class DigiBankActivity : BaseActivity(), DigiBankPresenter.View {

    private val presenter = DigiBankPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_box)

        presenter.view = this

        presenter.requestDataBoxList()

        setupActionBar()
        setupViews()
    }

    override fun setupActionBar() {
        viewActivityTitle.text = getString(R.string.digibank)

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

    override fun inflateDataBoxList() {
        viewDataBoxList?.run {
            addItemDecoration(EdgeDecorator(16))
            adapter = DataBoxAdapter(presenter.getDataBoxList(), ::changePartner)
            layoutManager = LinearLayoutManager(this@DigiBankActivity)
        }
    }

    private fun changePartner(monster: Monster){
        Session.user?.let{
            if(it.partner.id != monster.id) {
                showBottomSheetDialog(
                        title = "Trocar de Parceiro",
                        message = "Você quer que ${monster.species} seja seu parceiro a partir de agora?",
                        confirmButtonLabel = getString(R.string.yes),
                        cancelButtonLabel = getString(R.string.no),
                        confirmButtonAction = {
                            presenter.changePartner(monster)
                        }
                )
            }
        }
    }

    override fun changeBuddyMessage(monster: String, image: String) {
        showBottomSheetDialog(
                title = "Parceiro",
                message = "${monster} a partir de agora é o seu parceiro e vai ajudar você nas batalhas.",
                image = image,
                confirmButtonLabel = getString(R.string.ok)
        )
    }

    override fun refreshDataBoxList() {
        viewDataBoxList?.adapter?.notifyDataSetChanged()
    }
}
