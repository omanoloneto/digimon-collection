package co.hillstech.digicolle.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.hillstech.digicolle.DataBases.DBOthers
import co.hillstech.digicolle.MainActivity
import co.hillstech.digicolle.R
import co.hillstech.digicolle.Session

class SplashActivity : AppCompatActivity() {

    var dbo: DBOthers = DBOthers(this, null, null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        verifyPreferences()

        startActivity(Intent(this, LoginActivity::class.java))

    }

    private fun verifyPreferences() {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val username = preferences.getString("username", null)
        val password = preferences.getString("password", null)

        if (username != null && password != null) {
            Session.username = username
            Session.password = password
        }
    }
}
