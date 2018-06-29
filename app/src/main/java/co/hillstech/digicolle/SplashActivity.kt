package co.hillstech.digicolle

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import co.hillstech.digicolle.DataBases.DBOthers

class SplashActivity : AppCompatActivity() {

    var dbo: DBOthers = DBOthers(this, null, null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        try{
            var user = dbo.getOther("userName")
            var crest = dbo.getOther("userCrest")
            if(user!!.value != null && user.value != ""){
                Session.user.name = user.value
                Session.user.crest = crest!!.value
                Handler().postDelayed({
                    startActivity(Intent(this, DigiviceActivity::class.java))
                    finish()
                }, 2000)
            }else{
                Handler().postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, 2000)
            }
        }catch (ex: Exception){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}
