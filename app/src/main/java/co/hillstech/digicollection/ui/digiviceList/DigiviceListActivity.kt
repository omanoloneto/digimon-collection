package co.hillstech.digicollection.ui.digiviceList

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.models.Digivice
import kotlinx.android.synthetic.main.activity_digivice_list.*
import kotlinx.android.synthetic.main.view_action_bar.*

class DigiviceListActivity : BaseActivity(), DigiviceListPresenter.View {

    private val presenter = DigiviceListPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digivice_list)

        presenter.view = this

        presenter.requestDigiviceList()

        setupActionBar()
        setupViews()
    }

    override fun setupActionBar() {
        viewActivityTitle.text = getString(R.string.my_digivices)

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }
    }

    override fun inflateDigiviceList() {
        viewDigiviceList?.run {
            addItemDecoration(EdgeDecorator(16))
            adapter = DigiviceListAdapter(presenter.getDigiviceList(), this@DigiviceListActivity::changeEquipedDigivice)
            layoutManager = LinearLayoutManager(this@DigiviceListActivity)
        }
    }

    override fun refreshEquipedDigivice(digivice: Digivice?) {
        digivice?.let {
            Session.user?.digivice = it
        }
    }

    override fun changeEquipedDigivice(digiviceId: Int) {
        presenter.changeEquipedDigivice(digiviceId)
    }

    override fun refreshDigiviceList() {
        viewDigiviceList?.adapter?.notifyDataSetChanged()
    }

    override fun showProgressRing() {
        progressRingCall(this)
    }

    override fun hideProgressRing() {
        progressRingDismiss()
    }
}
