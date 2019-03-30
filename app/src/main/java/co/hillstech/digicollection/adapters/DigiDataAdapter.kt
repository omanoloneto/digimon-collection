package co.hillstech.digicollection.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.models.DigiDataEntry
import co.hillstech.enums.DigiDataType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_digi_data_monster.view.*
import kotlinx.android.synthetic.main.item_digi_data_title.view.*
import co.hillstech.digicollection.R

/**
 * Created by Gunther Ribak on 25/03/2019.
 * for Copyright 2019 outdabox.in. All rights reserved.
 * you can contact me at: gribak@outdabox.in
 */
class DigiDataAdapter(val entries: MutableList<DigiDataEntry>): RecyclerView.Adapter<DigiDataAdapter.BaseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return if (viewType == DigiDataType.TITLE.type) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_digi_data_title, parent, false)
            TitleHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_digi_data_monster, parent, false)
            MonsterHolder(itemView)
        }
    }

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemViewType(position: Int): Int {
        return entries[position].type.type
    }

    fun update(entries: MutableList<DigiDataEntry>) {
        this.entries.clear()
        this.entries.addAll(entries)
        notifyDataSetChanged()
    }

    abstract class BaseHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(entry: DigiDataEntry)
    }

    class TitleHolder(itemView : View) : BaseHolder(itemView) {

        override fun bind(entry: DigiDataEntry) {
            with(itemView) {
                itmDigiDataTvTitle.text = entry.title
            }
        }

    }

    class MonsterHolder(itemView : View) : BaseHolder(itemView) {

        override fun bind(entry: DigiDataEntry) {
            with(itemView) {
                Picasso.get()
                        .load(entry.monster.image)
                        .placeholder(R.drawable.placeholder)
                        .into(itmDigiDataIvImage)
                itmDigiDataTvName.text = entry.monster.species
            }
        }

    }

}