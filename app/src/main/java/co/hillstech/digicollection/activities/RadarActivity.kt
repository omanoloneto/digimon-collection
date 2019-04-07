package co.hillstech.digicollection.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseActivity
import co.hillstech.digicollection.fragments.ScannerFragment
import co.hillstech.digicollection.models.ChargeResponse
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.models.MonsterResponse
import co.hillstech.digicollection.ui.battleResult.BattleResultActivity
import co.hillstech.digicollection.utils.showMessageDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_radar.*
import kotlinx.android.synthetic.main.view_action_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RadarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar)

        setupActivity()

        viewTrackBack.setOnClickListener {
            trackInfectedMonsters()
        }
    }

    override fun onResume() {
        super.onResume()
        setupActivity()
        setupDigiviceInfos()
    }

    private fun setupDigiviceInfos() {
        Session.user?.digivice?.let {

            setupDigiviceCharge(it.charge)

            checkDigiviceCharge()

            Picasso.get().load(it.image)
                    .noPlaceholder()
                    .into(viewDigivice)
        }
    }

    private fun guardianBattle() {
        Session.guardians = mutableListOf<Monster>().apply {
            addAll(Session.location!!.bosses)
        }
        startActivity(Intent(this@RadarActivity, BattleResultActivity::class.java))
    }

    private fun trackInfectedMonsters() {
        progressRingCall(this)
        Session.user?.let {
            val call = UserService().location().getInfectedMonster(Session.location!!.id, it.digivice!!.id, it.id)

            call.enqueue(object : Callback<MonsterResponse?> {

                override fun onResponse(call: Call<MonsterResponse?>?,
                                        response: Response<MonsterResponse?>?) {
                    response?.body()?.let {

                        if (it.fieldTypeDontSet()) {
                            showMessageDialog(getString(R.string.warning), getString(R.string.do_you_want_to_send_your_current_location),
                                    positiveButtonLabel = getString(R.string.yes),
                                    negativeButtonLabel = getString(R.string.no),
                                    positiveButtonAction = {
                                        startActivity(Intent(this@RadarActivity, LocationActivity::class.java))
                                    })
                        } else if (it.isFieldClear()) {
                            Toast.makeText(this@RadarActivity, "Sem digimons por perto.", Toast.LENGTH_SHORT).show()
                        } else if (it.isFieldMonstersDontSet()) {
                            Toast.makeText(this@RadarActivity, "Nenhum digimon cadastrado nesta área.", Toast.LENGTH_SHORT).show()
                        } else if (it.isDigiviceNotCharged()) {
                            Toast.makeText(this@RadarActivity, "Digivice sem carga no momento.", Toast.LENGTH_SHORT).show()
                        } else {
                            var coinsReceived = it.coins
                            Session.user?.let {
                                it.wallet += coinsReceived
                                viewWallet.text = "$ ${it.wallet}"
                            }

                            it.monster?.let {
                                ScannerFragment().apply {
                                    wildId = it.id
                                    species = it.species
                                    image = it.image
                                    progress = Session.getScanProgress(it)
                                    coins = coinsReceived
                                }.show(supportFragmentManager, "Scanner Completed")
                            }
                        }

                        checkDigiviceCharge()

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }

                    progressRingDismiss()
                }

                override fun onFailure(call: Call<MonsterResponse?>?, t: Throwable?) {
                    progressRingDismiss()
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }

    private fun checkDigiviceCharge() {
        Session.user?.let {
            val call = UserService().user().checkDigiviceCharge(it.id, it.digivice!!.id)

            call.enqueue(object : Callback<ChargeResponse?> {

                override fun onResponse(call: Call<ChargeResponse?>?,
                                        response: Response<ChargeResponse?>?) {
                    response?.body()?.let {

                        setupDigiviceCharge(it.charge)

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<ChargeResponse?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }

    private fun setupDigiviceCharge(charge: Int) {
        Session.user?.digivice?.charge = charge
        viewDigiviceCharge.progress = charge
        viewDigivicePercent.text = "$charge%"
    }

    private fun setupActivity() {
        viewActivityTitle.text = getString(R.string.digi_radar)

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }

        setupDigiviceInfos()

        if (Session.location!!.clear) {
            viewBossList.alpha = 0.2f
            viewBossList.text = "    Guardiões derrotados    "
            viewBossList.setOnClickListener { }
        } else {
            viewBossList.setOnClickListener { guardianBattle() }
        }

        viewLocationTitle.text = Session.location!!.title
    }
}
