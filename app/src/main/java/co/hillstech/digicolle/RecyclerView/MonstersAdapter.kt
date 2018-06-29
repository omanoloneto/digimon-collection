package co.hillstech.digicolle.RecyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.hillstech.digicolle.Classes.Monster
import co.hillstech.digicolle.R

class MonstersAdapter(private val mons: List<Monster>,
                      private val context: Context) : RecyclerView.Adapter<MonstersHolder>() {

    override fun onBindViewHolder(holder: MonstersHolder, position: Int) {
        val mon = mons[position]

        holder?.let{
            it.bindView(mon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonstersHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_monsters, parent, false)
        return MonstersHolder(view)
    }

    override fun getItemCount(): Int {
        return mons.size
    }

}