package co.hillstech.digicollection.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_scanner.*

class ScannerFragment : DialogFragment() {

    var title: String = ""
    var species: String = ""
    var image: String? = null
    var progress: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scanner, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewScannerTitle.text =
                if(title != "") title
                else getString(R.string.scan_completed)

        viewScannerSpecies.text = species
        viewProgressBar.progress = progress
        viewScannerPercent.text = "$progress%"

        image?.let {
            Picasso.get().load(it)
                    .placeholder(R.drawable.placeholder)
                    .into(viewScannerImage)
        }

        viewConfirmButton.setOnClickListener { dismiss() }
    }

}