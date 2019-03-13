package co.hillstech.digicollection.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_digivice.*

class DigiviceFragment : DialogFragment() {

    var model       : String = ""
    var maxLevel    : String = ""
    var cooldown    : Int = 0
    var resume      : String = ""
    var image       : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_digivice, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDigiviceModel.text = model
        viewResume.text = resume
        viewMaxLevel.text = maxLevel
        viewCooldown.text = "${cooldown} "+getString(R.string.minutes)

        image?.let {
            Picasso.get().load(it)
                    .placeholder(R.drawable.placeholder)
                    .into(viewDigiviceImage)
        }

        viewConfirmButton.setOnClickListener { dismiss() }
    }

}