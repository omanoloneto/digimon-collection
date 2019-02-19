package co.hillstech.digicolle.activities

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

        try{
            var user = dbo.getOther("userName")
            var crest = dbo.getOther("userCrest")
            if(user!!.value != null && user.value != ""){
                Session.user.name = user.value
                Session.user.crest = crest!!.value
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }catch (ex: Exception){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}
