package co.hillstech.digicollection.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import com.onesignal.OneSignal


class SplashActivity : AppCompatActivity() {

    var username: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

        with(Session.spotlightConfig) {
            introAnimationDuration = 300
            isRevealAnimationEnabled = true
            isPerformClick = true
            fadingTextDuration = 300
            headingTvSize = 32
            subHeadingTvSize = 16
            maskColor = Color.parseColor("#dc333333")
            lineAnimationDuration = 300
        }

        if (isUserLogged()) {
            Session.authenticate(this, username, password)
        } else {
            startActivity(Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
    }

    private fun isUserLogged(): Boolean {
        val preferences = applicationContext
                .getSharedPreferences("DigiCollePref", MODE_PRIVATE)

        username = preferences.getString("username", null) ?: ""
        password = preferences.getString("password", null) ?: ""

        return username != "" && password != ""
    }
}
