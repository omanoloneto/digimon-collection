package co.hillstech.digicollection.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.fragments.ScannerFragment
import co.hillstech.digicollection.models.ChargeResponse
import co.hillstech.digicollection.models.MonsterResponse
import co.hillstech.digicollection.utils.showMessageDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_radar.*
import kotlinx.android.synthetic.main.view_action_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            progressRingCall(this)
            if (longitude == "" && latitude == "") {
                try {
                    locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                } catch (e: SecurityException) {
                    Log.e("ERROR", e.message)
                }
            } else {
                trackInfectedMonsters()
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
            var initialize = false
            if (longitude == "" && latitude == "") {
                initialize = true
            }

            longitude = location.longitude.toString()
            latitude = location.latitude.toString()

            Session.latitude = location.latitude
            Session.longitude = location.longitude

            if (initialize) {
                trackInfectedMonsters()
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun trackInfectedMonsters() {
        Session.user?.let {
            val call = UserService().location().getInfectedMonster(latitude, longitude, it.digivice!!.id, it.id)

            call.enqueue(object : Callback<MonsterResponse?> {

                override fun onResponse(call: Call<MonsterResponse?>?,
                                        response: Response<MonsterResponse?>?) {
                    response?.body()?.let {

                        if (it.fieldTypeDontSet()) {
                            showMessageDialog(getString(R.string.warning), getString(R.string.do_you_want_to_send_your_current_location),
                                    positiveButtonLabel = getString(R.string.yes),
                                    negativeButtonLabel = getString(R.string.no),
                                    positiveButtonAction = {
                                        startActivity(Intent(this@RadarActivity, LocationActivity::class.java))
                                    })
                        } else if (it.isFieldClear()) {
                            Toast.makeText(this@RadarActivity, "Sem digimons por perto.", Toast.LENGTH_SHORT).show()
                        } else if (it.isFieldMonstersDontSet()) {
                            Toast.makeText(this@RadarActivity, "Nenhum digimon cadastrado nesta área.", Toast.LENGTH_SHORT).show()
                        } else if (it.isDigiviceNotCharged()) {
                            Toast.makeText(this@RadarActivity, "Digivice sem carga no momento.", Toast.LENGTH_SHORT).show()
                        } else {
                            var coinsReceived = it.coins
                            Session.user?.let {
                                it.wallet += coinsReceived
                                viewWallet.text = "$ ${it.wallet}"
                            }

                            it.monster?.let {
                                ScannerFragment().apply {
                                    species = it.species
                                    image = it.image
                                    progress = Session.getScanProgress(it)
                                    coins = coinsReceived
                                }.show(supportFragmentManager, "Scanner Completed")
                            }
                        }

                        checkDigiviceCharge()

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }

                    progressRingDismiss()
                }

                override fun onFailure(call: Call<MonsterResponse?>?, t: Throwable?) {
                    progressRingDismiss()
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }

    private fun checkDigiviceCharge() {
        Session.user?.let {
            val call = UserService().user().checkDigiviceCharge(it.id, it.digivice!!.id)

            call.enqueue(object : Callback<ChargeResponse?> {

                override fun onResponse(call: Call<ChargeResponse?>?,
                                        response: Response<ChargeResponse?>?) {
                    response?.body()?.let {

                        setupDigiviceCharge(it.charge)

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<ChargeResponse?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }

    private fun setupDigiviceCharge(charge: Int) {
        Session.user?.digivice?.charge = charge
        viewDigiviceCharge.progress = charge
        viewDigivicePercent.text = "$charge%"
    }

    private fun setupActivity() {
        viewActivityTitle.text = getString(R.string.digi_radar)

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }

        Session.user?.digivice?.let {

            setupDigiviceCharge(it.charge)

            checkDigiviceCharge()

            Picasso.get().load(it.image)
                    .noPlaceholder()
                    .into(viewDigivice)
        }
    }
}
