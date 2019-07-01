package co.hillstech.digicollection.ui.digiDex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import kotlinx.android.synthetic.main.activity_digi_dex.*

class DigiDexActivity : BaseActivity(), DigiDexPresenter.View {

    var presenter = DigiDexPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digi_dex)

        presenter.view = this

        presenter.requestMonsterList()
    }

    override fun inflateMonsterList() {
        viewMonsterList?.apply {
            addItemDecoration(EdgeDecorator(16))
            adapter = DigiDexAdapter(presenter.getMonsterList(), {})
            layoutManager = GridLayoutManager(this@DigiDexActivity, 3)
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
