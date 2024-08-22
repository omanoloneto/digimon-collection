package co.hillstech.digicollection.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (isUserLogged()) {
            Session.authenticate(this, username, password)
        } else {
            navigateToLogin()
        }
    }

    private fun isUserLogged(): Boolean {
        val preferences = getSharedPreferences("DigiCollePref", MODE_PRIVATE)

        username = preferences.getString("username", "") ?: ""
        password = preferences.getString("password", "") ?: ""

        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}
