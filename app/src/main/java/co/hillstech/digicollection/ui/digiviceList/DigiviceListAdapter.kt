package co.hillstech.digicollection.ui.digiviceList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.models.Digivice
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_digivice_list_items.view.*

class DigiviceListAdapter(private val items: List<Digivice>,
                          private val changeEquipedDigivice: (Int) -> Unit) : RecyclerView.Adapter<ScanListHolder>() {

    override fun onBindViewHolder(holder: ScanListHolder, position: Int) {
        val item = items[position]
        holder.bindView(item, changeEquipedDigivice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_digivice_list_items, parent, false)
        return ScanListHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ScanListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(item: Digivice,
                 changeEquipedDigivice: (Int) -> Unit) = with(itemView) {

        viewModel.text = item.model
        viewCooldown.text = "${item.cooldown} " + itemView.context.getString(R.string.minutes)
        viewMaxEvolution.text = item.maxLevel

        if (item.equiped) {
            viewCheckedCircle.visibility = View.VISIBLE
            viewUncheckedCircle.visibility = View.GONE
        } else {
            viewCheckedCircle.visibility = View.GONE
            viewUncheckedCircle.visibility = View.VISIBLE
            viewUncheckedCircle.setOnClickListener {
                changeEquipedDigivice(item.id)
            }
        }

        Picasso.get().load(item.image)
                .placeholder(R.drawable.placeholder)
                .into(viewImage)
    }

}