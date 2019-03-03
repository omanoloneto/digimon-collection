package co.hillstech.digicollection.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import kotlinx.android.synthetic.main.activity_radar.*
import kotlinx.android.synthetic.main.view_action_bar.*

class RadarActivity : BaseActivity() {

    private var locationManager: LocationManager? = null
    private val FINE_REQUEST_CODE = 1
    private val COARSE_REQUEST_CODE = 2

    private var longitude: String = ""
    private var latitude: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar)

        setupActivity()
        checkPermissions()

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        viewTrackBack.setOnClickListener {
            if(longitude != "" && latitude != ""){
                try {
                    locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                } catch (e: SecurityException) {
                    Log.e("ERROR", e.message)
                }
            }
        }
    }

    private fun checkPermissions() {
        val fineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)

        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            makeFineLocationRequest()
        }

        val coarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            makeCoarseLocationRequest()
        }
    }

    private fun makeFineLocationRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_REQUEST_CODE)
    }

    private fun makeCoarseLocationRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                COARSE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            FINE_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    onBackPressed()
                }
            }
            COARSE_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    onBackPressed()
                }
            }
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            longitude = location.longitude.toString()
            latitude = location.latitude.toString()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun setupActivity() {
        viewActivityTitle.text = getString(R.string.digi_radar)

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }
    }
}
