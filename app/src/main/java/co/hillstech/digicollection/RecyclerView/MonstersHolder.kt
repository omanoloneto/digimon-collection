package co.hillstech.digicollection.RecyclerView

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import co.hillstech.digicollection.*
import co.hillstech.digicollection.EvolutionsActivity
import co.hillstech.digicollection.MainActivity
import co.hillstech.digicollection.Classes.Monster
import co.hillstech.digicollection.DataBases.DBHandler
import co.hillstech.digicollection.DataBases.DBOthers
import kotlinx.android.synthetic.main.view_monsters.view.*


class MonstersHolder : RecyclerView.ViewHolder, View.OnCreateContextMenuListener {
    fun bindView(mon: Monster) {

        itemView.txtId.text = "${mon.id}"
        itemView.txtName.text = "${mon.name}"
        itemView.txtLvl.text = itemView.context.getString(R.string.level)+": ${mon.lvl}"
        itemView.imgMonster.setImageResource(Monster.getSpecie(mon.name.toString()).image)

    }

    constructor(itemView: View):super(itemView){
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        val partner = menu?.add(Menu.NONE, 1, 1, v!!.context.getString(R.string.set_buddy))
        partner?.setOnMenuItemClickListener{
            var x = v?.txtId?.text
            var id = Integer.parseInt(x.toString())
            var db: DBHandler = DBHandler(v!!.context, null, null, 1)
            db.setPartner(id)
            val intent = Intent(v!!.getContext(), MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            v!!.getContext().startActivity(intent)
            true
        }
        val evos = menu?.add(Menu.NONE, 2, 2, v!!.context.getString(R.string.evo_line))
        evos?.setOnMenuItemClickListener{
            var x = v?.txtName?.text
            Session.specie = x.toString()
            val intent = Intent(v!!.getContext(), EvolutionsActivity::class.java)
            v!!.getContext().startActivity(intent)
            true
        }

        var db: DBHandler = DBHandler(v!!.context, null, null, 1)
        var dbo: DBOthers = DBOthers(v!!.context, null, null, 1)

        val sell = menu?.add(Menu.NONE, 3, 3, v!!.context.getString(R.string.sell_monster))
        sell?.setOnMenuItemClickListener{
            var x = v?.txtName?.text
            var y = Integer.parseInt(v?.txtId?.text.toString())

            var coins = Monster.getSpecie(x.toString()).percent

            var tirar = coins*150

            coins = 5000-tirar

            val dialogBuilder = AlertDialog.Builder(v!!.context)
            dialogBuilder.setTitle(v!!.context.getString(R.string.want_sell_title))
            dialogBuilder.setMessage(v!!.context.getString(R.string.want_sell_body)+" $coins "+v!!.context.getString(R.string.want_sell_end)+"\n")
            dialogBuilder.setPositiveButton(v!!.context.getString(R.string.yes), DialogInterface.OnClickListener { dialog, whichButton ->

                db.delete(y)
                var coinsdb = dbo.getOther("coins")
                if(coinsdb != null){
                    coinsdb.value = (Integer.parseInt(coinsdb.value) + coins).toString()
                    dbo.setOther(coinsdb)
                }

                val intent = Intent(v!!.getContext(), MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                v!!.getContext().startActivity(intent)
            })
            dialogBuilder.setNegativeButton(v!!.context.getString(R.string.nop), null)
            dialogBuilder.show()

            //Session.specie = x.toString()
            //val intent = Intent(v!!.getContext(), EvolutionsActivity::class.java)
            //v!!.getContext().startActivity(intent)
            true
        }
    }

}