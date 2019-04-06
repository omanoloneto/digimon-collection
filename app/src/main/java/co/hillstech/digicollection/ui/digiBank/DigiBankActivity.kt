package co.hillstech.digicollection.ui.digiBank

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.enums.MonsterLevel
import co.hillstech.digicollection.enums.getAttribute
import co.hillstech.digicollection.enums.getElement
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.utils.showBottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_data_box.*
import kotlinx.android.synthetic.main.view_action_bar.*
import kotlinx.android.synthetic.main.view_digimon_details.view.*
import android.support.design.widget.BottomSheetBehavior
import android.widget.FrameLayout
import co.hillstech.digicollection.enums.toString
import kotlinx.android.synthetic.main.view_rename_monster.view.*


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
            adapter = DataBoxAdapter(presenter.getDataBoxList(), ::onMonsterClick)
            layoutManager = LinearLayoutManager(this@DigiBankActivity)
        }
    }

    private fun onMonsterClick(monster: Monster){
        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.view_digimon_details, null)

        with(view) {
            viewDetailName.text = monster.nick ?: monster.species
            viewDetailSpecies.text = monster.species

            Picasso.get().load(monster.image)
                    .placeholder(R.drawable.placeholder)
                    .into(viewDetailImage)

            viewDetailLevel.text = Session.monsterLevel(this@DigiBankActivity, monster.type)
            viewDetailElemet.text = monster.element.getElement(this@DigiBankActivity)
            viewDetailAttribute.text = monster.attribute.getAttribute(this@DigiBankActivity)
            viewDetailDescription.text = monster.description
            viewDetailPerson.text = monster.personality.toString(this@DigiBankActivity)

            viewDetailHP.text = monster.base_hp.toString()
            viewDetailATK.text = monster.base_atk.toString()
            viewDetailDEF.text = monster.base_def.toString()
            viewDetailSPATK.text = monster.base_sp_atk.toString()
            viewDetailSPDEF.text = monster.base_sp_def.toString()
            viewDetailSPD.text = monster.base_spd.toString()

            Session.user?.let {
                if (it.partner.id != monster.id) {
                    viewDetailBuddyButton.visibility = View.VISIBLE
                    viewDetailBuddyButton.setOnClickListener {
                        changePartner(monster)
                        dialog.dismiss()
                    }
                }else{
                    viewDetailBuddyButton.visibility = View.GONE
                }
            }

            viewDetailNickButton.setOnClickListener {
                renameMonster(monster)
                dialog.dismiss()
            }

            viewDetailBackButton.setOnClickListener {
                dialog.dismiss()
            }

        }

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.setOnShowListener{
            val bottomSheet = dialog.findViewById(android.support.design.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet!!).setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        dialog.show()
    }

    private fun renameMonster(monster: Monster){
        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.view_rename_monster, null)

        with(view) {
            viewAlertDialogTitle.text = this@DigiBankActivity.getString(R.string.rename)
            viewAlertDialogMessage.text = this@DigiBankActivity.getString(R.string.enter_in_the_field_below_the_new_name)

            viewConfirmButton.setOnClickListener {
                monster.nick = viewRenameDigimonLabel.text.toString()

                val index = presenter.viewModel.indexOfFirst { it.id == monster.id }
                presenter.viewModel[index].nick = monster.nick
                refreshDataBoxList()

                presenter.rename(monster)

                onMonsterClick(monster)
                dialog.dismiss()
            }

            viewCancelButton.setOnClickListener {
                onMonsterClick(monster)
                dialog.dismiss()
            }

        }

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.setOnShowListener{
            val bottomSheet = dialog.findViewById(android.support.design.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet!!).setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        dialog.show()
    }

    private fun changePartner(monster: Monster){
        showBottomSheetDialog(
                title = "Trocar de Parceiro",
                message = "Você quer que ${monster.species} seja seu parceiro a partir de agora?",
                confirmButtonLabel = getString(R.string.yes),
                cancelButtonLabel = getString(R.string.no),
                confirmButtonAction = {
                    presenter.changePartner(monster)
                },
                cancelButtonAction = {
                    onMonsterClick(monster)
                }
        )
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
