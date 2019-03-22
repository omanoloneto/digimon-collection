package co.hillstech.digicollection.ui.evolutionList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.models.Monster
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_digivice_list_items.view.*
import kotlinx.android.synthetic.main.view_evolution_line_monster.view.*

class EvolutionListAdapter(private val items: List<Monster>) : RecyclerView.Adapter<EvolutionListHolder>() {

    override fun onBindViewHolder(holder: EvolutionListHolder, position: Int) {
        val item = items[position]
        holder.bindView(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_evolution_line_monster, parent, false)
        return EvolutionListHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class EvolutionListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(item: Monster) = with(itemView) {
        viewEvolutionSpecies.text = item.species
        viewEvolutionElement.text = item.element

        Picasso.get().load(item.image)
                .placeholder(R.drawable.placeholder)
                .into(viewEvolutionImage)
    }

}