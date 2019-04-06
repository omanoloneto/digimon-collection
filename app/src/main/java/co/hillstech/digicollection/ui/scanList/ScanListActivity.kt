package co.hillstech.digicollection.ui.scanList

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.utils.showBottomSheetDialog
import kotlinx.android.synthetic.main.activity_scan_list.*
import kotlinx.android.synthetic.main.view_action_bar.*

class ScanListActivity : BaseActivity(), ScanListPresenter.View {

    private val presenter = ScanListPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_list)
        presenter.view = this
        setupViews()
        setupActionBar()
    }

    override fun setupViews() {
        viewScanList?.run {
            addItemDecoration(EdgeDecorator(16))
            adapter = ScanListAdapter(presenter.getScanList(), ::convertMonster)
            layoutManager = LinearLayoutManager(this@ScanListActivity)
        }
    }

    private fun convertMonster(monster: Monster){
        if(monster.progress >= 100) {
            showBottomSheetDialog(
                    title = "DigiConvert",
                    message = "Você quer usar o seu DigiCode para converter um ${monster.species} para você?",
                    confirmButtonLabel = getString(R.string.yes),
                    cancelButtonLabel = getString(R.string.no),
                    confirmButtonAction = {
                        presenter.convertMonster(monster)
                    }
            )
        }else{
            showBottomSheetDialog(
                    title = "Atenção",
                    message = "Você não tem DigiCode suficiente para converter um ${monster.species} para você.",
                    confirmButtonLabel = getString(R.string.ok)
            )
        }
    }

    override fun setupActionBar() {
        viewActivityTitle.text = getString(R.string.scan_list)

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

    override fun refreshScanList() {
        viewScanList?.adapter?.notifyDataSetChanged()
    }

    override fun convertMessage(monster: String, image: String) {
        showBottomSheetDialog(
                title = "Parabéns!",
                message = "O seu ${monster} foi convertido com sucesso e agora está esperando por você em seu DigiBank.",
                image = image,
                confirmButtonLabel = getString(R.string.ok)
        )
    }
}
