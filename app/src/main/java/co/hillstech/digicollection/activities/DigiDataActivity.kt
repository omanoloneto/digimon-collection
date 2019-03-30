package co.hillstech.digicollection.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.hillstech.digicollection.adapters.DigiDataAdapter
import kotlinx.android.synthetic.main.activity_digi_data.*

class DigiDataActivity : AppCompatActivity() {

    private val adp : DigiDataAdapter by lazy {
        DigiDataAdapter(mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_digi_data)
        //setTitle(R.string.digidata)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(actDigiDataRcv) {
            adapter = adp
        }
    }
}
