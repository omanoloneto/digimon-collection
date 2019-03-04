package co.hillstech.digicollection.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Digivice
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_store_item.view.*

class DigiviceAdapter (private val digivices: List<Digivice>,
                       private val context: Context,
                       private val updateWallet: () -> Unit,
                       private val updateDigivice: () -> Unit) : RecyclerView.Adapter<DigiviceHolder>() {

    override fun onBindViewHolder(holder: DigiviceHolder, position: Int) {
        val digivice = digivices[position]
        holder.bindView(digivice, updateWallet, updateDigivice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigiviceHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_store_item, parent, false)
        return DigiviceHolder(view)
    }

    override fun getItemCount(): Int {
        return digivices.size
    }
}

class DigiviceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(digivice: Digivice, updateWallet: () -> Unit, updateDigivice: () -> Unit) = with(itemView){
        viewItemName.text = digivice.model
        viewItemPrice.text = "$ "+digivice.price

        Picasso.get().load(digivice.image)
                .noPlaceholder()
                .into(viewItemImage)

        if(digivice.bought) {
            viewBuyButton.text = context.getString(R.string.bought)
            viewItem.alpha = 0.5f
        } else if(digivice.price > Session.user!!.wallet) {
            viewBuyButton.text = context.getString(R.string.no_money)
            viewBuyButton.alpha = 0.5f
        } else {
            viewBuyButton.setOnClickListener {
                Session.user?.let {
                    if(it.digivice == null){
                        digivice.apply {
                            it.digivice = Digivice(
                                    id,
                                    model,
                                    price,
                                    image.replace(
                                            "${digivice.id}.png",
                                            "${digivice.id}/${it.crest.id}.png"
                                    ),
                                    bought
                            )
                        }
                    }
                    it.wallet -= digivice.price
                    digivice.bought = true
                    updateWallet()
                    updateDigivice()

                    Toast.makeText(context, "Você comprou um ${digivice.model}!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}