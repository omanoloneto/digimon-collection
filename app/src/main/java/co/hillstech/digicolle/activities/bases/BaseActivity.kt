package co.hillstech.digicolle.activities.bases

import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import co.hillstech.digicolle.R

abstract class BaseActivity : AppCompatActivity() {

    protected fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.regal_blue)
        }
    }
}