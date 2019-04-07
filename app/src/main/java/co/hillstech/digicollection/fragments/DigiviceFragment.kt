package co.hillstech.digicollection.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.ui.digiviceList.DigiviceListActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_digivice.*

class DigiviceFragment : DialogFragment() {

    var model: String = ""
    var maxLevel: String = ""
    var cooldown: Int = 0
    var resume: String = ""
    var image: String? = null
    var showButtons: Boolean = true
    var blockBack: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_digivice, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDigiviceModel.text = model
        viewResume.text = resume
        viewMaxLevel.text = maxLevel
        viewCooldown.text = "${cooldown} " + getString(R.string.minutes)

        image?.let {
            Picasso.get().load(it)
                    .placeholder(R.drawable.placeholder)
                    .into(viewDigiviceImage)
        }

        if (showButtons) {
            viewConfirmButton.setOnClickListener {
                startActivity(Intent(activity!!, DigiviceListActivity::class.java))
                dismiss()
            }

            viewCancelButton.setOnClickListener { dismiss() }
        } else {
            viewConfirmButton.visibility = View.GONE
            viewCancelButton.visibility = View.GONE
        }

        if(blockBack) isCancelable = false
    }

}