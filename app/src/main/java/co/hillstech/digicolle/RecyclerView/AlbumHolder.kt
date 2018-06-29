package co.hillstech.digicolle.RecyclerView

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v7.widget.RecyclerView
import android.view.View
import co.hillstech.digicolle.Classes.Monster
import co.hillstech.digicolle.EvolutionsActivity
import co.hillstech.digicolle.Session
import kotlinx.android.synthetic.main.view_album.view.*

class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(mon: Monster) {

        itemView.imgAlbum.setImageResource(mon.image)

        if(!Session.seens.contains(mon.name)){
            itemView.txtAlbumName.text = " "

            val matrix = ColorMatrix()
            matrix.setSaturation(0f)

            val filter = ColorMatrixColorFilter(matrix)
            itemView.imgAlbum.setColorFilter(filter)

            itemView.crvFigura.alpha = 0.20f

            itemView.crvFigura.setOnClickListener {
                //click(mon.name.toString())
            }

        }else{
            itemView.txtAlbumName.text = mon.name

            val matrix = ColorMatrix()
            matrix.setSaturation(1f)

            val filter = ColorMatrixColorFilter(matrix)
            itemView.imgAlbum.setColorFilter(filter)

            itemView.crvFigura.alpha = 1f

            itemView.crvFigura.setOnClickListener{
                click(mon.name.toString())
            }
        }

    }

    fun click(mon: String){
        Session.specie = mon
        val intent = Intent(itemView.getContext(), EvolutionsActivity::class.java)
        itemView.getContext().startActivity(intent)
    }
}