package co.hillstech.digicollection.ui.scan

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
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
            adapter = ScanListAdapter(presenter.getScanList())
            layoutManager = LinearLayoutManager(this@ScanListActivity)
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
}
