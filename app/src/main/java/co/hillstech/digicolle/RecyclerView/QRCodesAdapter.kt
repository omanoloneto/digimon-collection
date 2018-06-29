package co.hillstech.digicolle.RecyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.hillstech.digicolle.Classes.QRCodes
import co.hillstech.digicolle.R

class QRCodesAdapter(private val codes: List<QRCodes>,
                     private val context: Context) : RecyclerView.Adapter<QRCodesHolder>() {

    override fun onBindViewHolder(holder: QRCodesHolder, position: Int) {
        val code = codes[position]

        holder?.let{
            it.bindView(code)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QRCodesHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_qrcodes, parent, false)
        return QRCodesHolder(view)
    }

    override fun getItemCount(): Int {
        return codes.size
    }
}