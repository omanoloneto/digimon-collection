package co.hillstech.digicollection.ui.scan

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.models.Monster
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_scan_progress.view.*

class ScanListAdapter(private val items: List<Monster>) : RecyclerView.Adapter<ScanListHolder>() {

    override fun onBindViewHolder(holder: ScanListHolder, position: Int) {
        val item = items[position]
        holder.bindView(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_scan_progress, parent, false)
        return ScanListHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ScanListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(item: Monster) = with(itemView) {
        viewSpecies.text = item.species
        viewProgress.text = "${item.progress}%"
        viewProgressBar.progress = item.progress

        Picasso.get().load(item.image)
                .placeholder(R.drawable.placeholder)
                .into(viewImage)
    }

}