package co.hillstech.digicollection.RecyclerView

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v7.widget.RecyclerView
import android.view.View
import co.hillstech.digicollection.Classes.Monster
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import kotlinx.android.synthetic.main.view_scanning.view.*

class ScanningHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(mon: Monster) {

        itemView.txtId.text = "${mon.id}"
        itemView.txtName.text = "${mon.name}"
        itemView.txtProgress.text = itemView.context.getString(R.string.progress)+" ${mon.scan}%"
        itemView.imgMonster.setImageResource(Monster.getSpecie(mon.name.toString()).image)

        if(!Session.seens.contains(mon.name)){
            val matrix = ColorMatrix()
            matrix.setSaturation(0f)

            val filter = ColorMatrixColorFilter(matrix)
            itemView.imgMonster.setColorFilter(filter)
            itemView.imgMonster.alpha = 0.20f
        }else{
            val matrix = ColorMatrix()
            matrix.setSaturation(1f)

            val filter = ColorMatrixColorFilter(matrix)
            itemView.imgMonster.setColorFilter(filter)
            itemView.imgMonster.alpha = 1f
        }

    }
}