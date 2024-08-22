package co.hillstech.digicollection.activities

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.databinding.ActivityLocationBinding
import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.utils.showMessageDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationActivity : BaseActivity() {

    private lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivity()

        binding.btnEnviar.setOnClickListener {
            val lat = Session.latitude ?: return@setOnClickListener
            val lon = Session.longitude ?: return@setOnClickListener

            val latmin = lat - 0.001f
            val latmax = lat + 0.001f
            val lonmin = lon - 0.001f
            val lonmax = lon + 0.001f

            val tipo = getSelectedTypes()

            sendLocation(
                lat.toString(),
                lon.toString(),
                latmin.toString(),
                lonmin.toString(),
                latmax.toString(),
                lonmax.toString(),
                tipo
            )
        }
    }

    private fun sendLocation(
        lat: String, lon: String,
        latmin: String, lonmin: String,
        latmax: String, lonmax: String,
        tipo: String
    ) {
        showProgressRing(this)

        Session.user?.let {
            val call =
                UserService().location().insert(lat, lon, lonmin, lonmax, latmin, latmax, tipo)

            call.enqueue(object : Callback<BooleanResponse?> {

                override fun onResponse(
                    call: Call<BooleanResponse?>,
                    response: Response<BooleanResponse?>
                ) {
                    response.body()?.let {
                        showMessageDialog(
                            getString(R.string.success),
                            getString(R.string.your_location_was_successfully_registered),
                            positiveButtonLabel = getString(R.string.ok),
                            positiveButtonAction = { finish() }
                        )
                    } ?: run {
                        Log.e("ERROR", response.errorBody()?.string() ?: "Unknown error")
                    }
                    dismissProgressRing()
                }

                override fun onFailure(call: Call<BooleanResponse?>, t: Throwable) {
                    Log.e("ERROR", t.message ?: "Unknown error")
                    dismissProgressRing()
                }
            })
        }
    }

    private fun setupActivity() {
        with(binding.include2) {
            viewActivityTitle.text = getString(R.string.send_location)
            viewBackArrow.setOnClickListener { onBackPressed() }

            Session.user?.crest?.color?.let { color ->
                setStatusBarColor(color)
                viewActionBar.setCardBackgroundColor(Color.parseColor(color))
            }
        }
    }

    private fun getSelectedTypes(): String {
        val types = StringBuilder()

        with(binding) {
            if (checkBox1.isChecked) types.append("13,")
            if (checkBox2.isChecked) types.append("12,")
            if (checkBox3.isChecked) types.append("11,")
            if (checkBox4.isChecked) types.append("10,")
            if (checkBox5.isChecked) types.append("9,")
            if (checkBox6.isChecked) types.append("8,")
            if (checkBox7.isChecked) types.append("7,")
            if (checkBox8.isChecked) types.append("6,")
            if (checkBox9.isChecked) types.append("5,")
            if (checkBox10.isChecked) types.append("4,")
            if (checkBox11.isChecked) types.append("3,")
            if (checkBox12.isChecked) types.append("2,")
            if (checkBox13.isChecked) types.append("1,")
        }

        // Remove the trailing comma
        if (types.isNotEmpty() && types.last() == ',') {
            types.setLength(types.length - 1)
        }

        return types.toString()
    }
}
