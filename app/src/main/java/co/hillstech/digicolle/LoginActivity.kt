package co.hillstech.digicolle

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import co.hillstech.digicolle.DataBases.DBOthers
import co.hillstech.digicolle.Retrofit.CreateClass
import co.hillstech.digicolle.Retrofit.UserService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    var dbo: DBOthers = DBOthers(this, null, null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
                getString(R.string.connect), true)

        progress.show()

        //criando a variável de apiReturn da api
        var result: CreateClass

        //enviando as credentials para o webservice
        val call = UserService().login().exe(name,pass)

        //executando o request e tratando o response
        call.enqueue(object: Callback<CreateClass?> {

            //sucesso no request
            override fun onResponse(call: Call<CreateClass?>?,
                                    response: Response<CreateClass?>?) {
                response?.body()?.let {
                    result = it

                    Handler().postDelayed({

                        progress.dismiss()

                        if(result.status == "true"){

                            //to-do

                            var user = dbo.getOther("userName")
                            if(user!!.term != null){
                                user.value = result.data!![0].name
                                dbo.setOther(user)
                            }else{
                                dbo.add("userName",result.data!![0].name.toString())
                            }

                            Session.user.name = result.data!![0].name.toString()

                            var crest = dbo.getOther("userCrest")
                            if(crest!!.term != null){
                                crest.value = result.data!![0].crest
                                dbo.setOther(crest)
                            }else{
                                dbo.add("userCrest",result.data!![0].crest.toString())
                            }

                            Session.user.name = result.data!![0].crest.toString()

                            startActivity(Intent(this@LoginActivity,DigiviceActivity::class.java))
                            finish()

                        }else{

                            //to-do
                            val dialogBuilder = AlertDialog.Builder(this@LoginActivity)
                            dialogBuilder.setTitle(getString(R.string.login_erro))
                            dialogBuilder.setMessage(getString(R.string.login_naocriado))
                            dialogBuilder.setPositiveButton("OK", null)
                            dialogBuilder.show()

                        }

                    }, 1000)

                } ?: run {

                    //to-do

                }

            }

            //falha ao executar o request
            override fun onFailure(call: Call<CreateClass?>?,
                                   t: Throwable?) {

                //to-do

            }
        })
    }

}
