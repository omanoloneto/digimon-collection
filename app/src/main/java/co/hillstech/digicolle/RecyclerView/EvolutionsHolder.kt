package co.hillstech.digicolle.RecyclerView

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v7.widget.RecyclerView
import android.view.View
import co.hillstech.digicolle.Classes.Evolution
import co.hillstech.digicolle.Classes.Monster
import co.hillstech.digicolle.EvolutionsActivity
import co.hillstech.digicolle.Session
import kotlinx.android.synthetic.main.view_evolutions.view.*

class EvolutionsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(mon: Evolution) {

        itemView.imgEvo.setImageResource(Monster.getSpecie(mon.evo.toString()).image)

        if(!Session.seens.contains(mon.evo)){
            val matrix = ColorMatrix()
            matrix.setSaturation(0f)

            val filter = ColorMatrixColorFilter(matrix)
            itemView.imgEvo.setColorFilter(filter)
            itemView.imgEvo.alpha = 0.20f

            itemView.crvEvos.setOnClickListener { /* null */ }
        }else{
            val matrix = ColorMatrix()
            matrix.setSaturation(1f)

            val filter = ColorMatrixColorFilter(matrix)
            itemView.imgEvo.setColorFilter(filter)
            itemView.imgEvo.alpha = 1f

            itemView.crvEvos.setOnClickListener{
                Session.specie = mon.evo
                val intent = Intent(itemView.getContext(), EvolutionsActivity::class.java)
                itemView.getContext().startActivity(intent)
            }
        }

    }
}