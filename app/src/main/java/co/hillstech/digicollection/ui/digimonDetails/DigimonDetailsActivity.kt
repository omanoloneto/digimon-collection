package co.hillstech.digicollection.ui.digimonDetails

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.FrameLayout
import co.hillstech.digicollection.R
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.enums.getAttribute
import co.hillstech.digicollection.enums.getElement
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_digimon_details.*
import kotlinx.android.synthetic.main.view_action_bar.*
import kotlinx.android.synthetic.main.view_digimon_details.*
import kotlinx.android.synthetic.main.view_locations.view.*

class DigimonDetailsActivity : BaseActivity(), DigimonDetailsPresenter.View {

    var presenter = DigimonDetailsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digimon_details)

        presenter.view = this

        setupActionBar(presenter.getMonster().species)
        setupViews()
    }

    override fun setupViews() {
        super.setupViews()

        setViewsToGone()

        with(presenter.getMonster()) {
            viewDetailSpecies.text = species

            Picasso.get().load(image)
                    .placeholder(R.drawable.placeholder)
                    .into(viewDetailImage)

            val matrix = ColorMatrix()

            if (scanned) {
                viewDetailImage.alpha = 1f
                matrix.setSaturation(1f)

                viewDetailHP.text = base_hp.toString()
                viewDetailATK.text = base_atk.toString()
                viewDetailDEF.text = base_def.toString()
                viewDetailSPATK.text = base_sp_atk.toString()
                viewDetailSPDEF.text = base_sp_def.toString()
                viewDetailSPD.text = base_spd.toString()
            } else {
                viewDetailImage.alpha = 0.1f
                matrix.setSaturation(0f)

                viewDetailHP.text = "--"
                viewDetailATK.text = "--"
                viewDetailDEF.text = "--"
                viewDetailSPATK.text = "--"
                viewDetailSPDEF.text = "--"
                viewDetailSPD.text = "--"
            }

            viewDetailImage.colorFilter = ColorMatrixColorFilter(matrix)

            viewDetailElemet.text = element?.getElement(this@DigimonDetailsActivity)
            viewDetailAttribute.text = attribute?.getAttribute(this@DigimonDetailsActivity)
            viewDetailDescription.text = description

            viewDetailMap.setOnClickListener { showLocations(species, locations) }
        }

    }

    private fun setViewsToGone() {
        View.GONE.let {
            viewWallet.visibility = it
            layoutCoinsIcon.visibility = it
            viewDetailName.visibility = it
            viewDetailLevel.visibility = it
            viewDetailPerson.visibility = it
            layoutDetailLevel.visibility = it
            layoutDetailPerson.visibility = it
            viewDetailNickButton.visibility = it
            viewDetailBackButton.visibility = it
        }
    }

    private fun showLocations(species: String, locations: List<String>) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.view_locations, null)

        with(view) {
            viewLocationsList?.run {
                adapter = DigimonDetailsAdapter(locations, {})
                layoutManager = LinearLayoutManager(this@DigimonDetailsActivity)
            }
        }

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById(android.support.design.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet!!).setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        dialog.show()
    }
}
