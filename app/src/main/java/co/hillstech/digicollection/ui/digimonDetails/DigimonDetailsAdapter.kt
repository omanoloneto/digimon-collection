package co.hillstech.digicollection.ui.digimonDetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import kotlinx.android.synthetic.main.view_location_dex_item.view.*

class DigimonDetailsAdapter(private val items: List<String>,
                            private val onItemClickListner: (String) -> Unit) : RecyclerView.Adapter<DigimonDetailsHolder>() {

    override fun onBindViewHolder(holder: DigimonDetailsHolder, position: Int) {
        val item = items[position]
        holder.bindView(item, onItemClickListner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigimonDetailsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_location_dex_item, parent, false)
        return DigimonDetailsHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class DigimonDetailsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(item: String, onItemClickListner: (String) -> Unit) = with(itemView) {
        viewLocationName.text = item
        layoutLocationDexItem.setOnClickListener { onItemClickListner(item) }
    }

}