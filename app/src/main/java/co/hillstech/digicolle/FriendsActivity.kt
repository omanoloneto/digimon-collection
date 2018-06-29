package co.hillstech.digicolle

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class FriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        var ac = supportActionBar
        ac!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#024d91")))
    }
}
