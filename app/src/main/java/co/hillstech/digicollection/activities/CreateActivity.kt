package co.hillstech.digicollection.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.CreateClass
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.activities.bases.BaseActivity
import kotlinx.android.synthetic.main.activity_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        setStatusBarColor()

        btnCreate.setOnClickListener {
            if (txtName.text.toString() == "" || txtPass.text.toString() == "") {
                Toast.makeText(this, getString(R.string.create_empty_message), Toast.LENGTH_SHORT).show()
            } else {
                createAccount(txtName.text.toString(),
                        txtPass.text.toString())
            }
        }
    }

    fun createAccount(name: String, pass: String) {

        val progress = ProgressDialog.show(this, "",
                getString(R.string.connect), true)

        progress.show()

        //criando a variável de apiReturn da api
        var result: CreateClass

        //enviando as credentials para o webservice
        val call = UserService().newUser().exe(name, pass)

        //executando o request e tratando o response
        call.enqueue(object : Callback<CreateClass?> {

            //sucesso no request
            override fun onResponse(call: Call<CreateClass?>?,
                                    response: Response<CreateClass?>?) {
                response?.body()?.let {
                    result = it

                    Handler().postDelayed({

                        progress.dismiss()

                        if (result.status == "true") {

                            //to-do
                            val dialogBuilder = AlertDialog.Builder(this@CreateActivity)
                            dialogBuilder.setTitle(getString(R.string.create_sucesso))
                            dialogBuilder.setMessage(getString(R.string.create_criado))
                            dialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, whichButton ->
                                finish()
                            })
                            dialogBuilder.show()

                        } else {

                            //to-do
                            val dialogBuilder = AlertDialog.Builder(this@CreateActivity)
                            dialogBuilder.setTitle(getString(R.string.create_erro))
                            dialogBuilder.setMessage(getString(R.string.create_naocriado))
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
