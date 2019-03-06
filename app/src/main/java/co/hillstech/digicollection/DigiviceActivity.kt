package co.hillstech.digicollection

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import co.hillstech.digicollection.activities.LocationActivity
import kotlinx.android.synthetic.main.activity_digivice.*

class DigiviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digivice)

        var ac = supportActionBar
        ac!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#024d91")))

        /*if(Session.user.crest == "0" || Session.user.crest == null){
            var num = Random().nextInt(11)+1
            imgDigivice.setImageResource(User.getDigiviceDrawable(num))
            Session.color = User.getDigiviceColor(num)
            ac!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(Session.color)))
        }*/

        imgDigivice.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.btnFriends){
            startActivity(Intent(this,FriendsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
