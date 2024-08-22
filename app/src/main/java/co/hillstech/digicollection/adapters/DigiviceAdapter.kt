package co.hillstech.digicollection.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Crest
import co.hillstech.digicollection.models.Digivice
import com.squareup.picasso.Picasso

class DigiviceAdapter(
    private val digivices: List<Digivice>,
    private val context: Context,
    private val updateWallet: () -> Unit,
    private val updateDigivice: (Int, Boolean) -> Unit,
    private val changeDigivice: (Digivice) -> Unit,
    private val digiviceDetails: (Digivice) -> Unit
) : RecyclerView.Adapter<DigiviceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigiviceHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_store_item, parent, false)
        return DigiviceHolder(view)
    }

    override fun onBindViewHolder(holder: DigiviceHolder, position: Int) {
        holder.bindView(
            digivices[position],
            updateWallet,
            updateDigivice,
            changeDigivice,
            digiviceDetails
        )
    }

    override fun getItemCount(): Int = digivices.size
}

class DigiviceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(
        digivice: Digivice,
        updateWallet: () -> Unit,
        updateDigivice: (Int, Boolean) -> Unit,
        changeDigivice: (Digivice) -> Unit,
        digiviceDetails: (Digivice) -> Unit
    ) = with(itemView) {

        val viewItemName: TextView = itemView.findViewById(R.id.viewItemName)
        val viewItemPrice: TextView = itemView.findViewById(R.id.viewItemPrice)
        val viewItem: View = itemView.findViewById(R.id.viewItem)
        val viewBuyButton: Button = itemView.findViewById(R.id.viewBuyButton)
        val viewItemImage: ImageView = itemView.findViewById(R.id.viewItemImage)

        viewItemName.text = digivice.model
        viewItemPrice.text = "$ " + digivice.price

        Picasso.get().load(digivice.image)
            .placeholder(R.drawable.placeholder)
            .into(viewItemImage)

        viewItem.setOnClickListener { digiviceDetails(digivice) }

        if (digivice.bought) {
            viewBuyButton.text = context.getString(R.string.bought)
            viewBuyButton.isEnabled = false
            viewItem.alpha = 0.5f
        } else if (digivice.price > Session.user?.wallet ?: 0) {
            viewBuyButton.text = context.getString(R.string.no_money)
            viewBuyButton.isEnabled = false
            viewBuyButton.alpha = 0.5f
        } else {
            viewBuyButton.text = "Comprar"
            viewBuyButton.isEnabled = true
            viewBuyButton.alpha = 1.0f

            viewBuyButton.setOnClickListener {
                Session.user?.let { user ->
                    user.wallet -= digivice.price
                    digivice.bought = true
                    updateWallet()

                    val newDigivice = getDigivice(digivice, user.crest)

                    if (user.digivice == null) {
                        user.digivice = newDigivice
                        updateDigivice(digivice.id, true)
                    } else {
                        changeDigivice(newDigivice)
                    }

                    Toast.makeText(
                        context,
                        "Você comprou um ${digivice.model}!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getDigivice(digivice: Digivice, crest: Crest): Digivice {
        return Digivice(
            digivice.id,
            digivice.model,
            digivice.price,
            100,
            digivice.cooldown,
            digivice.image.replace(
                "${digivice.id}.png",
                "${digivice.id}/${crest.id}.png"
            ),
            digivice.resume,
            digivice.maxLevel,
            digivice.bought,
            digivice.equiped
        )
    }
}
