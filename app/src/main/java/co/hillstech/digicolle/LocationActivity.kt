package co.hillstech.digicolle

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
import co.hillstech.digicolle.Retrofit.CreateClass
import co.hillstech.digicolle.Retrofit.UserService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private val TAG = "MainActivity"
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null
    private val listener: com.google.android.gms.location.LocationListener? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    lateinit var locationManager: LocationManager

    var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        var ac = supportActionBar
        ac!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#024d91")))

        //ctlBody.background = ColorDrawable(Color.parseColor(Session.color))

        btnPega.setOnClickListener {
            getLocation()
        }

        btnEnviar.setOnClickListener {
            var lat: Double = mLocation.latitude
            var lon: Double = mLocation.longitude

            var latmin = lat-0.001f
            var latmax = lat+0.001f

            var lonmin = lon-0.001f
            var lonmax = lon+0.001f

            var tipo = getRadioChecked()

            sendLocation(lat.toString(),
                    lon.toString(),
                    latmin.toString(),
                    lonmin.toString(),
                    latmax.toString(),
                    lonmax.toString(),
                    tipo.toString())
        }

        MultiDex.install(this)

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        checkLocation()
    }

    fun sendLocation(lat: String, lon: String,
                     latmin: String, lonmin: String,
                     latmax: String, lonmax: String,
                     tipo: String){

        val progress = ProgressDialog.show(this, "",
                getString(R.string.connect), true)

        progress.show()

        //criando a variável de apiReturn da api
        var result: CreateClass

        //enviando as credentials para o webservice
        val call = UserService().setLocation().exe(lat,lon,lonmin,lonmax,latmin,latmax,tipo)

        //executando o request e tratando o response
        call.enqueue(object: Callback<CreateClass?> {

            //sucesso no request
            override fun onResponse(call: Call<CreateClass?>?,
                                    response: Response<CreateClass?>?) {
                response?.body()?.let {
                    result = it

                    Handler().postDelayed({

                        progress.dismiss()

                        if(result.status == "true"){

                            //to-do
                            val dialogBuilder = android.app.AlertDialog.Builder(this@LocationActivity)
                            dialogBuilder.setTitle("Sucesso!")
                            dialogBuilder.setMessage("Obrigado por ajudar o projeto. Sua sugestão de localização foi cadastrada com sucesso!")
                            dialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                                finish()
                            })
                            dialogBuilder.show()


                        }else{

                            //to-do
                            val dialogBuilder = android.app.AlertDialog.Builder(this@LocationActivity)
                            dialogBuilder.setTitle("Erro...")
                            dialogBuilder.setMessage("Infelizmente não conseguimos salvar sua localização. Por favor, tente novamente mais tarde!")
                            dialogBuilder.setPositiveButton("OK", null)
                            dialogBuilder.show()

                        }

                    }, 1000)

                } ?: run {

                    //to-do

                }

            }

            //falha ao executar o request
            override fun onFailure(call: Call<CreateClass?>?,
                                   t: Throwable?) {

                //to-do

            }
        })
    }

    fun getRadioChecked(): Int{
        if(rdbTipo1.isChecked) return 1
        else if(rdbTipo2.isChecked) return 2
        else if(rdbTipo3.isChecked) return 3
        else if(rdbTipo4.isChecked) return 4
        else if(rdbTipo5.isChecked) return 5
        else if(rdbTipo6.isChecked) return 6
        else if(rdbTipo7.isChecked) return 7
        else if(rdbTipo8.isChecked) return 8
        else if(rdbTipo9.isChecked) return 9
        else if(rdbTipo10.isChecked) return 10
        else if(rdbTipo11.isChecked) return 11
        else if(rdbTipo12.isChecked) return 12
        else if(rdbTipo13.isChecked) return 13
        else return 0
    }

    override fun onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    override fun onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    override fun onConnectionSuspended(p0: Int) {

        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    override fun onLocationChanged(p0: Location?) {
        location = p0
    }

    fun getLocation() {


        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 2)
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 2)
            return;
        }

        var fusedLocationProviderClient :
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient .getLastLocation()
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation = location;
                        txt_latitude.setText("" + mLocation.latitude)
                        txt_longitude.setText("" + mLocation.longitude)
                    }
                })


    }

    override fun onConnected(p0: Bundle?) {


        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 2)
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 2)
            return;
        }


        startLocationUpdates();

        var fusedLocationProviderClient :
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient .getLastLocation()
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation = location;
                        //txt_latitude.setText("" + mLocation.latitude)
                        //txt_longitude.setText("" + mLocation.longitude)
                    }
                })
    }

    private fun checkLocation(): Boolean {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    protected fun startLocationUpdates() {

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 2)
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 2)
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }
}
