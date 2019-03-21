package co.hillstech.digicollection.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.adapters.DigiviceAdapter
import co.hillstech.digicollection.fragments.DigiviceFragment
import co.hillstech.digicollection.fragments.ScannerFragment
import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.models.Digivice
import co.hillstech.digicollection.models.StoreResponse
import co.hillstech.digicollection.utils.showMessageDialog
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_action_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        setupActivity()

        getItems()
    }

    private fun updateWallet() {
        Session.user?.let {
            val call = UserService().user().updateWallet(it.id.toString(), it.wallet)

            call.enqueue(object : Callback<BooleanResponse?> {

                override fun onResponse(call: Call<BooleanResponse?>?,
                                        response: Response<BooleanResponse?>?) {
                    response?.body()?.let {
                        viewWallet.text = "$ ${Session.user?.wallet}"
                        Log.e("SUCCESS", it.status.toString())
                    } ?: run {
                        Log.e("ERROR", "ERROR")
                    }
                }

                override fun onFailure(call: Call<BooleanResponse?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }

    private fun updateDigivice(digiviceId: Int, equipe: Boolean = true) {
        Session.user?.let {
            val call = UserService().user().updateDigivice(it.id.toString(), digiviceId, equipe)

            call.enqueue(object : Callback<BooleanResponse?> {

                override fun onResponse(call: Call<BooleanResponse?>?,
                                        response: Response<BooleanResponse?>?) {
                    response?.body()?.let {
                        viewDigivicesList.adapter!!.notifyDataSetChanged()
                        Log.e("SUCCESS", it.status.toString())
                    } ?: run {
                        Log.e("ERROR", "ERROR")
                    }
                }

                override fun onFailure(call: Call<BooleanResponse?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }

    private fun digiviceDetails(digivice: Digivice){
        digivice.let {
            DigiviceFragment().apply {
                model = it.model
                cooldown = it.cooldown
                maxLevel = it.maxLevel
                resume = it.resume
                image = it.image
            }.show(supportFragmentManager, "DIGIVICE_FRAGMENT")
        }
    }

    private fun changeDigivice(digivice: Digivice){
        showMessageDialog(getString(R.string.warning), getString(R.string.do_you_want_change_your_digivice)+" ${digivice.model}?",
            positiveButtonLabel = getString(R.string.yes),
            negativeButtonLabel = getString(R.string.no),
            positiveButtonAction = {
                Session.user?.let {
                    it.digivice = digivice
                    updateDigivice(digivice.id, true)
                }
            },
            negativeButtonAction = {
                updateDigivice(digivice.id, false)
            }
        )
    }

    private fun getItems() {

        progressRingCall(this)

        val call = UserService().store().getItems(Session.user?.id.toString())

        call.enqueue(object : Callback<StoreResponse?> {

            override fun onResponse(call: Call<StoreResponse?>?,
                                    response: Response<StoreResponse?>?) {
                response?.body()?.let {
                    progressRingDismiss()

                    viewDigivicesList?.adapter = DigiviceAdapter(it.digivices, this@StoreActivity,
                            ::updateWallet, ::updateDigivice, ::changeDigivice, ::digiviceDetails)
                    viewDigivicesList?.layoutManager = LinearLayoutManager(this@StoreActivity, LinearLayoutManager.HORIZONTAL, false)

                } ?: run {

                    progressRingDismiss()

                }
            }

            override fun onFailure(call: Call<StoreResponse?>?,
                                   t: Throwable?) {

                progressRingDismiss()
                Log.e("ERROR", t?.message)

            }
        })
    }

    private fun setupActivity() {
        viewActivityTitle.text = getString(R.string.store)
        viewWallet.text = "$ " + Session.user?.wallet.toString()

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }
    }
}
