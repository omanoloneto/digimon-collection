package co.hillstech.digicollection.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import com.onesignal.OneSignal
import com.wooplr.spotlight.SpotlightConfig

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        verifyPreferences()

        OneSignal.startInit(this)
                 .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                 .unsubscribeWhenNotificationsAreDisabled(true)
                 .init()

        Session.spotlightConfig.introAnimationDuration = 300
        Session.spotlightConfig.isRevealAnimationEnabled = true
        Session.spotlightConfig.isPerformClick = true
        Session.spotlightConfig.fadingTextDuration = 300
        Session.spotlightConfig.headingTvSize = 32
        Session.spotlightConfig.subHeadingTvSize = 16
        Session.spotlightConfig.maskColor = Color.parseColor("#dc333333")
        Session.spotlightConfig.lineAnimationDuration = 300

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
