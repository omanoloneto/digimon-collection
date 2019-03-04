package co.hillstech.digicollection.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: create more options
    }

    override fun onResume() {
        super.onResume()
        updateUserHome()
    }

    private fun updateUserHome() {
        Session.user?.partner?.let {
            viewPartnerName.text = it.species
            Picasso.get().load(it.image)
                    .noPlaceholder()
                    .into(viewPartnerImage)
        }

        Session.user?.digivice?.let {
            layoutDigivice.visibility = View.VISIBLE
            viewDigiviceModel.text = it.model+" of "+Session.user!!.crest.virtue
            Picasso.get().load(it.image)
                    .noPlaceholder()
                    .into(viewDigiviceImage)
        }
    }
}
