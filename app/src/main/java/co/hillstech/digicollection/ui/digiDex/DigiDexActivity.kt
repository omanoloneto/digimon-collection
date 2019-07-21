package co.hillstech.digicollection.ui.digiDex

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.ui.digimonDetails.DigimonDetailsActivity
import kotlinx.android.synthetic.main.activity_digi_dex.*
import kotlinx.android.synthetic.main.view_action_bar.*

class DigiDexActivity : BaseActivity(), DigiDexPresenter.View {

    var presenter = DigiDexPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digi_dex)

        presenter.view = this

        setupActionBar()
        setupViews()

        presenter.requestMonsterList()
    }

    override fun setupActionBar() {
        viewActivityTitle.text = getString(R.string.digidex)

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }
    }

    override fun inflateMonsterList() {
        viewMonsterList?.apply {
            adapter = DigiDexAdapter(presenter.getMonsterList(), ::onItemClickListner)
            layoutManager = GridLayoutManager(this@DigiDexActivity, 3)
        }

        refreshProgressCount()
    }

    private fun onItemClickListner(monster: Monster) {
        Session.digimon = monster
        startActivity(Intent(this@DigiDexActivity, DigimonDetailsActivity::class.java))
    }

    private fun refreshProgressCount() {
        with(presenter.getMonsterList()) {
            viewProgressBar.max = size
            viewProgressBar.progress = filter { it.scanned }.size
            viewProgressText.text = "${viewProgressBar.progress} / $size"
        }
    }

    override fun showTutorial() {
        // code
    }

    override fun showProgressRing() {
        progressRingCall(this)
    }

    override fun hideProgressRing() {
        progressRingDismiss()
    }
}
