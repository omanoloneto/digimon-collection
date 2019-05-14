package co.hillstech.digicollection.ui.eventList

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.hillstech.digicollection.R
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.bases.BaseFragment
import co.hillstech.digicollection.models.Event
import kotlinx.android.synthetic.main.fragment_event_list.*

class EventListFragment : BaseFragment(), EventListPresenter.View {

    private var presenter = EventListPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this
        presenter.requestEventList()
    }

    override fun showProgressRing() {
        //layoutContainer.visibility = View.GONE
        //viewProgressRing.visibility = View.VISIBLE
    }

    override fun hideProgressRing() {
        //viewProgressRing.visibility = View.GONE
        //layoutContainer.visibility = View.VISIBLE
    }

    override fun inflateEventList() {
        viewEventList?.run {
            addItemDecoration(EdgeDecorator(16))
            adapter = EventListAdapter(presenter.getEventList(), ::onEventClick)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun onEventClick(event: Event) {
        Toast.makeText(activity, event.name, Toast.LENGTH_SHORT).show()
    }

}
