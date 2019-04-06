package co.hillstech.digicollection.activities

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.models.User
import co.hillstech.digicollection.models.UserResponse
import co.hillstech.digicollection.utils.showMessageDialog
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.onesignal.OneSignal
import org.json.JSONObject




class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setStatusBarColor()

        txtCreate.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        btnLogin.setOnClickListener {
            if (txtNameL.text.toString() == "" || txtPassL.text.toString() == "") {
                Toast.makeText(this, getString(R.string.create_empty_message), Toast.LENGTH_SHORT).show()
            } else {
                val progress = ProgressDialog.show(this, "",
                        getString(co.hillstech.digicollection.R.string.connect), true)

                progress.show()
                Session.authenticate(this, txtNameL.text.toString(), txtPassL.text.toString())
            }
        }
    }

}
