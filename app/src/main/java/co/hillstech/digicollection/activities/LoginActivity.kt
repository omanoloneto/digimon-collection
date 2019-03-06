package co.hillstech.digicollection.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.models.UserResponse
import co.hillstech.digicollection.models.User
import co.hillstech.digicollection.utils.showMessageDialog
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
                getString(co.hillstech.digicollection.R.string.connect), true)

        progress.show()

        val call = UserService().login().exe(name,pass)

        call.enqueue(object: Callback<UserResponse?> {

            override fun onResponse(call: Call<UserResponse?>?,
                                    response: Response<UserResponse?>?) {
                response?.body()?.let {
                    progress.dismiss()

                    if(it.status){

                        val preferences = applicationContext
                                .getSharedPreferences("DigiCollePref", MODE_PRIVATE)

                        preferences.edit()
                                   .putString("username", name)
                                   .putString("password", pass)
                                   .commit()

                        Session.user = it.data as User

                        startActivity(Intent(this@LoginActivity, LobbyActivity::class.java))
                        finish()

                    }else{

                        showMessageDialog(getString(R.string.error), getString(R.string.user_or_password_is_invalid), getString(R.string.ok))

                    }

                } ?: run {

                    progress.dismiss()
                    showMessageDialog(getString(R.string.error), getString(R.string.user_or_password_is_invalid), getString(R.string.ok))

                }
            }

            override fun onFailure(call: Call<UserResponse?>?,
                                   t: Throwable?) {
                progress.dismiss()
                showMessageDialog(getString(R.string.error), getString(R.string.user_or_password_is_invalid), getString(R.string.ok))
                Log.e("ERROR", t?.message)

            }
        })
    }

}
