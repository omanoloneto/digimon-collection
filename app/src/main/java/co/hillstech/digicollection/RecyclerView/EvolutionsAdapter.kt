package co.hillstech.digicollection.RecyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.hillstech.digicollection.Classes.Evolution
import co.hillstech.digicollection.R

class EvolutionsAdapter(private val mons: List<Evolution>,
                        private val context: Context) : RecyclerView.Adapter<EvolutionsHolder>() {

    override fun onBindViewHolder(holder: EvolutionsHolder, position: Int) {
        val mon = mons[position]

        holder?.let{
            it.bindView(mon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionsHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_evolutions, parent, false)
        return EvolutionsHolder(view)
    }

    override fun getItemCount(): Int {
        return mons.size
    }
}