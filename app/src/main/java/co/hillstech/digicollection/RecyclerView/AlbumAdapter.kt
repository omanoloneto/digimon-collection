package co.hillstech.digicollection.RecyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.hillstech.digicollection.Classes.Monster
import co.hillstech.digicollection.R

class AlbumAdapter(private val mons: List<Monster>,
                   private val context: Context) : RecyclerView.Adapter<AlbumHolder>() {

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        val mon = mons[position]

        holder?.let{
            it.bindView(mon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_album, parent, false)
        return AlbumHolder(view)
    }

    override fun getItemCount(): Int {
        return mons.size
    }
}