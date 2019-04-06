package co.hillstech.digicollection.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Location
import kotlinx.android.synthetic.main.view_location_item.view.*

class LocationAdapter(private val locations: List<Location>,
                      private val onLocationClick: (Location) -> Unit) : RecyclerView.Adapter<LocationHolder>() {

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        val location = locations[position]
        holder.bindView(location, onLocationClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_location_item, parent, false)
        return LocationHolder(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}

class LocationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(location: Location, onLocationClick: (Location) -> Unit) = with(itemView) {
        viewLocationTitle.text = location.title

        if(location.unlocked){
            layoutLocationContainer.alpha =  1.0f
            viewLocationLock.visibility = View.GONE
            layoutLocationContainer.setOnClickListener {
                onLocationClick(location)
            }
        }else{
            layoutLocationContainer.alpha =  0.2f
            viewLocationLock.visibility = View.VISIBLE
        }

    }

}