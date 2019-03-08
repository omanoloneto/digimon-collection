package co.hillstech.digicollection.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.models.Menu
import kotlinx.android.synthetic.main.view_menu_item.view.*

class MenuAdapter(private val menus: List<Menu>,
                  private val context: Context) : RecyclerView.Adapter<MenuHolder>() {

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val menu = menus[position]
        holder.bindView(menu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_menu_item, parent, false)
        return MenuHolder(view)
    }

    override fun getItemCount(): Int {
        return menus.size
    }
}

class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(menu: Menu) = with(itemView) {
        viewTitle.text = menu.title
        viewIcon.setImageDrawable(menu.icon)

        viewMenuItem.setOnClickListener {
            menu.onClickAction()
        }
    }

}