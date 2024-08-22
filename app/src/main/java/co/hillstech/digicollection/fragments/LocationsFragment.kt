package co.hillstech.digicollection.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.RadarActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.adapters.LocationAdapter
import co.hillstech.digicollection.databinding.FragmentLocationsBinding
import co.hillstech.digicollection.models.Location
import co.hillstech.digicollection.utils.showMessageDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    private var locationList: List<Location> = listOf()
    private var progressRing: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
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
        Session.user?.let { user ->
            val call = UserService().location().getLocations(user.id)
            call.enqueue(object : Callback<List<Location>?> {

                override fun onResponse(
                    call: Call<List<Location>?>,
                    response: Response<List<Location>?>
                ) {
                    response.body()?.let { locations ->
                        locationList = locations
                        if (!refresh) {
                            setupLocationList()
                        }
                        hideProgressRing()
                    } ?: run {
                        Log.e("ERROR", response.errorBody()?.string() ?: "Unknown error")
                        hideProgressRing()
                    }
                }

                override fun onFailure(call: Call<List<Location>?>, t: Throwable) {
                    Log.e("ERROR", t.message ?: "Unknown error")
                    hideProgressRing()
                }
            })
        }
    }

    private fun onLocationClick(location: Location) {
        if (Session.user?.digivice == null) {
            activity?.showMessageDialog(
                getString(R.string.warning),
                getString(R.string.you_must_have_a_digivice_to_use_the_radar),
                positiveButtonLabel = getString(R.string.ok)
            )
        } else {
            Session.location = location
            startActivity(Intent(activity, RadarActivity::class.java))
        }
    }

    private fun refreshLocationList() {
        binding.viewLocationList.adapter?.notifyDataSetChanged()
    }

    private fun setupLocationList() {
        activity?.let {
            binding.viewLocationList.apply {
                addItemDecoration(EdgeDecorator(16))
                adapter = LocationAdapter(locationList, ::onLocationClick)
                layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            }
            refreshLocationList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
