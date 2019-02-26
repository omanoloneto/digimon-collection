package co.hillstech.digicolle.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.hillstech.digicolle.R
import co.hillstech.digicolle.Retrofit.UserService
import co.hillstech.digicolle.Session
import co.hillstech.digicolle.activities.bases.BaseActivity
import co.hillstech.digicolle.models.UserResponse
import co.hillstech.digicolle.models.User
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setStatusBarColor()

        if(Session.username != null && Session.password != null){
            login(Session.username!!, Session.password!!)
        }

        txtCreate.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        btnLogin.setOnClickListener {
            if(txtNameL.text.toString() == "" || txtPassL.text.toString() == ""){
                Toast.makeText(this, getString(R.string.create_empty_message),Toast.LENGTH_SHORT).show()
            }else{
                login(txtNameL.text.toString(),
                        txtPassL.text.toString())
            }
        }
    }

    fun login(name: String, pass: String){

        val progress = ProgressDialog.show(this, "",
                getString(co.hillstech.digicolle.R.string.connect), true)

        progress.show()

        val call = UserService().login().exe(name,pass)

        call.enqueue(object: Callback<UserResponse?> {

            override fun onResponse(call: Call<UserResponse?>?,
                                    response: Response<UserResponse?>?) {
                response?.body()?.let {
                    progress.dismiss()

                    if(it.status){

                        val preferences = getPreferences(Context.MODE_PRIVATE)

                        preferences.edit()
                                   .putString("username", name)
                                   .putString("password", pass)
                                   .apply()

                        Session.user = it.data as User

                        startActivity(Intent(this@LoginActivity, LobbyActivity::class.java))
                        finish()

                    }else{

                        //to-do
                        val dialogBuilder = AlertDialog.Builder(this@LoginActivity)
                        dialogBuilder.setTitle(getString(R.string.login_erro))
                        dialogBuilder.setMessage(getString(R.string.login_naocriado))
                        dialogBuilder.setPositiveButton("OK", null)
                        dialogBuilder.show()

                    }

                } ?: run {

                    //to-do

                }
            }

            override fun onFailure(call: Call<UserResponse?>?,
                                   t: Throwable?) {

                Log.e("ERROR", t?.message)

            }
        })
    }

}
