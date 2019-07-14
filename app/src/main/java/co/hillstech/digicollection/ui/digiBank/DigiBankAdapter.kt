package co.hillstech.digicollection.ui.digiBank

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.enums.getAttribute
import co.hillstech.digicollection.enums.getElement
import co.hillstech.digicollection.models.Monster
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_data_box_item.view.*

class DataBoxAdapter(private val items: List<Monster>,
                     private val onMonsterClick: (Monster) -> Unit) : RecyclerView.Adapter<DataBoxHolder>() {

    override fun onBindViewHolder(holder: DataBoxHolder, position: Int) {
        val item = items[position]
        holder.bindView(item, onMonsterClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoxHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_data_box_item, parent, false)
        return DataBoxHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class DataBoxHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(item: Monster, onMonsterClick: (Monster) -> Unit) = with(itemView) {

        if (item.nick != null) {
            viewDataTitle.text = item.nick
            viewDataSpecies.visibility = View.VISIBLE
            viewDataSpecies.text = item.species
        } else {
            viewDataSpecies.visibility = View.GONE
            viewDataTitle.text = item.species
        }

        viewDataAttribute.text = item.attribute?.getAttribute(context)
        viewDataElement.text = item.element?.getElement(context)

        if (item.partner) {
            viewDataPartner.visibility = View.VISIBLE
        } else {
            viewDataPartner.visibility = View.GONE
        }

        layoutDigiBankItem.setOnClickListener {
            onMonsterClick(item)
        }

        Picasso.get().load(item.image)
                .placeholder(R.drawable.placeholder)
                .into(viewDataImage)
    }

}