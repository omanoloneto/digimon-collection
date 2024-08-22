package co.hillstech.digicollection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.hillstech.digicollection.R
import co.hillstech.digicollection.models.Location

class LocationAdapter(
    private val locations: List<Location>,
    private val onLocationClick: (Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_location_item, parent, false)
        return LocationHolder(view)
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bindView(locations[position], onLocationClick)
    }

    override fun getItemCount(): Int = locations.size

    class LocationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewLocationTitle: TextView = itemView.findViewById(R.id.viewLocationTitle)
        private val layoutLocationContainer: View =
            itemView.findViewById(R.id.layoutLocationContainer)
        private val viewLocationLock: ImageView = itemView.findViewById(R.id.viewLocationLock)

        fun bindView(location: Location, onLocationClick: (Location) -> Unit) {
            viewLocationTitle.text = location.title

            if (location.unlocked) {
                layoutLocationContainer.alpha = 1.0f
                viewLocationLock.visibility = View.GONE
                layoutLocationContainer.setOnClickListener { onLocationClick(location) }
            } else {
                layoutLocationContainer.alpha = 0.2f
                viewLocationLock.visibility = View.VISIBLE
                layoutLocationContainer.setOnClickListener(null)
            }
        }
    }
}
