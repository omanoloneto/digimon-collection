package co.hillstech.digicollection.ui.evolutionList

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import kotlinx.android.synthetic.main.activity_evolution_list.*
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

    override fun hideProgressRing() {

    }

    override fun showProgressRing() {

    }

    override fun inflateEvolutionList() {
        viewEvolutionList?.run {
            addItemDecoration(EdgeDecorator(16))
            adapter = EvolutionListAdapter(presenter.getEvolutionList())
            layoutManager = LinearLayoutManager(this@EvolutionListActivity)
        }
    }

    override fun refreshEvolutionList() {

    }
}
