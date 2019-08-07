package co.hillstech.digicollection.ui.eventList

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Event
import kotlinx.android.synthetic.main.view_event_list_item.view.*

class EventListAdapter(private val items: List<Event>,
                       private val onEventClick: (Event) -> Unit) : RecyclerView.Adapter<EventListHolder>() {

    override fun onBindViewHolder(holder: EventListHolder, position: Int) {
        val item = items[position]
        holder.bindView(item, onEventClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_event_list_item, parent, false)
        return EventListHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class EventListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(item: Event, onEventClick: (Event) -> Unit) = with(itemView) {

        Session.user?.crest?.color.let {
            viewEventFlag.setCardBackgroundColor(Color.parseColor(it))
        }

        if (item.isActive) {
            viewEventFlag.visibility = View.VISIBLE
            viewEventFlagText.text = context.getString(R.string.active)
        } else {
            viewEventFlag.visibility = View.GONE
        }

        viewEventName.text = item.name

        setOnClickListener { onEventClick(item) }
    }

}