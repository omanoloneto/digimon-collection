package co.hillstech.digicollection.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_store_item.view.*

class ItemAdapter(private val items: List<Item>,
                  private val itemDetails: (Item) -> Unit) : RecyclerView.Adapter<ItemHolder>() {

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.bindView(item, itemDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_store_item, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(item: Item, itemDetails: (Item) -> Unit) = with(itemView) {
        viewItemName.text = item.name
        viewItemPrice.text = "$ " + item.price

        Picasso.get().load(item.image)
                .noPlaceholder()
                .into(viewItemImage)

        viewBuyButton.text = "Detalhes"

        viewBuyButton.setOnClickListener {
            itemDetails(item)
        }
    }

}