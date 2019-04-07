package co.hillstech.digicollection.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.RadarActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.adapters.LocationAdapter
import co.hillstech.digicollection.models.Location
import co.hillstech.digicollection.utils.showMessageDialog
import kotlinx.android.synthetic.main.fragment_locations.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationsFragment : Fragment() {

    var locationList: List<Location> = listOf()
    private var progressRing: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_locations, container, false)
    }

    override fun onResume() {
        super.onResume()
        getLocationList()
    }

    private fun showProgressRing() {
        progressRing = ProgressDialog.show(context, "", "Carregando...", true)
        progressRing?.show()
    }

    private fun hideProgressRing() {
        progressRing?.dismiss()
    }

    private fun getLocationList(refresh: Boolean = false) {
        showProgressRing()
        Session.user?.let {
            val call = UserService().location().getLocations(it.id)

            call.enqueue(object : Callback<List<Location>?> {

                override fun onResponse(call: Call<List<Location>?>?,
                                        response: Response<List<Location>?>?) {
                    response?.body()?.let {

                        locationList = it
                        if (!refresh) {
                            setupLocationList()
                        }
                        hideProgressRing()

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                        hideProgressRing()
                    }
                }

                override fun onFailure(call: Call<List<Location>?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                    hideProgressRing()
                }
            })
        }
    }

    private fun onLocationClick(location: Location) {
        if(Session.user!!.digivice == null){
            activity?.showMessageDialog(getString(R.string.warning),
                    getString(R.string.you_must_have_a_digivice_to_use_the_radar),
                    positiveButtonLabel = getString(R.string.ok))
        }else {
            Session.location = location
            startActivity(Intent(activity, RadarActivity::class.java))
        }

    }

    private fun refreshLocationList() {
        viewLocationList?.adapter?.notifyDataSetChanged()
    }

    private fun setupLocationList() {
        activity?.let {
            viewLocationList?.addItemDecoration(EdgeDecorator(16))
            viewLocationList?.adapter = LocationAdapter(locationList, ::onLocationClick)
            viewLocationList?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            refreshLocationList()
        }
    }

}
