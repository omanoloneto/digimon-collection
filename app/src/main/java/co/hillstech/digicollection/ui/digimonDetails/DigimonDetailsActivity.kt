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

        setupActionBar()
        setupViews()
    }

    override fun setupActionBar() {
        viewActivityTitle.text = presenter.getMonster().species

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }
    }

    override fun setupViews() {
        super.setupViews()

    }
}
