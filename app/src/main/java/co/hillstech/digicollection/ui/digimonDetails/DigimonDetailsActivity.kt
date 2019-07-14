package co.hillstech.digicollection.ui.digimonDetails

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import kotlinx.android.synthetic.main.view_action_bar.*

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

    }
}
