package co.hillstech.digicollection.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.multidex.MultiDex
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.CreateClass
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.utils.showMessageDialog
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.view_action_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setupActivity()

        btnEnviar.setOnClickListener {
            var lat = Session.latitude!!
            var lon = Session.longitude!!

            var latmin = lat-0.001f
            var latmax = lat+0.001f

            var lonmin = lon-0.001f
            var lonmax = lon+0.001f

            var tipo = getCheckBoxCheckeds()

            sendLocation(lat.toString(),
                    lon.toString(),
                    latmin.toString(),
                    lonmin.toString(),
                    latmax.toString(),
                    lonmax.toString(),
                    tipo)
        }
    }

    private fun sendLocation(lat: String, lon: String,
                             latmin: String, lonmin: String,
                             latmax: String, lonmax: String,
                             tipo: String) {

        progressRingCall(this)

        Session.user?.let {
            val call = UserService().location().insert(lat, lon, lonmin, lonmax, latmin, latmax, tipo)

            call.enqueue(object : Callback<BooleanResponse?> {

                override fun onResponse(call: Call<BooleanResponse?>?,
                                        response: Response<BooleanResponse?>?) {
                    response?.body()?.let {
                        showMessageDialog(getString(R.string.success), getString(R.string.your_location_was_successfully_registered),
                                positiveButtonLabel = getString(R.string.ok),
                                positiveButtonAction = { (this@LocationActivity as Activity).finish() })
                        progressRingDismiss()
                    } ?: run {
                        progressRingDismiss()
                        Log.e("ERROR", response?.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<BooleanResponse?>?, t: Throwable?) {
                    progressRingDismiss()
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }

    private fun setupActivity() {
        viewActivityTitle.text = getString(R.string.send_location)

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }
    }

    fun getCheckBoxCheckeds(): String {
        var types = ""

        if(checkBox1.isChecked) types += "13,"
        if(checkBox2.isChecked) types += "12,"
        if(checkBox3.isChecked) types += "11,"
        if(checkBox4.isChecked) types += "10,"
        if(checkBox5.isChecked) types += "9,"
        if(checkBox6.isChecked) types += "8,"
        if(checkBox7.isChecked) types += "7,"
        if(checkBox8.isChecked) types += "6,"
        if(checkBox9.isChecked) types += "5,"
        if(checkBox10.isChecked) types += "4,"
        if(checkBox11.isChecked) types += "3,"
        if(checkBox12.isChecked) types += "2,"
        if(checkBox13.isChecked) types += "1,"

        types += ","
        types = types.replace(",,", "")

        return types
    }
}
