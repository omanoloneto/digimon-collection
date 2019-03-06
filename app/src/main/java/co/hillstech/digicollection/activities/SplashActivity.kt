package co.hillstech.digicollection.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        verifyPreferences()

        startActivity(Intent(this, LoginActivity::class.java))

    }

    private fun verifyPreferences() {
        val preferences = applicationContext
                .getSharedPreferences("DigiCollePref", MODE_PRIVATE)

        val username = preferences.getString("username", null)
        val password = preferences.getString("password", null)

        if (username != null && password != null) {
            Session.username = username
            Session.password = password
        }
    }
}
