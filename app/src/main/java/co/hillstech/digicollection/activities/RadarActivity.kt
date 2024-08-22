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
import co.hillstech.digicollection.databinding.ActivityRadarBinding
import co.hillstech.digicollection.fragments.ScannerFragment
import co.hillstech.digicollection.models.ChargeResponse
import co.hillstech.digicollection.models.MonsterResponse
import co.hillstech.digicollection.ui.battleResult.BattleResultActivity
import co.hillstech.digicollection.utils.showMessageDialog
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RadarActivity : BaseActivity() {

    private lateinit var binding: ActivityRadarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRadarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivity()

        binding.viewTrackBack.setOnClickListener {
            trackInfectedMonsters()
        }
    }

    override fun onResume() {
        super.onResume()
        setupActivity()
        setupDigiviceInfos()
    }

    private fun setupDigiviceInfos() {
        Session.user?.digivice?.let { digivice ->
            setupDigiviceCharge(digivice.charge)
            checkDigiviceCharge()

            Picasso.get().load(digivice.image)
                .noPlaceholder()
                .into(binding.viewDigivice)
        }
    }

    private fun guardianBattle() {
        Session.guardians = Session.location?.bosses?.toMutableList() ?: mutableListOf()
        startActivity(Intent(this@RadarActivity, BattleResultActivity::class.java))
    }

    private fun trackInfectedMonsters() {
        showProgressRing(this)
        Session.user?.let { user ->
            val call = UserService().location()
                .getInfectedMonster(Session.location!!.id, user.digivice!!.id, user.id)

            call.enqueue(object : Callback<MonsterResponse?> {

                override fun onResponse(
                    call: Call<MonsterResponse?>,
                    response: Response<MonsterResponse?>
                ) {
                    response.body()?.let { monsterResponse ->
                        handleMonsterResponse(monsterResponse)
                    } ?: run {
                        Log.e("ERROR", response.errorBody()?.string() ?: "Unknown error")
                    }
                    dismissProgressRing()
                }

                override fun onFailure(call: Call<MonsterResponse?>, t: Throwable) {
                    dismissProgressRing()
                    Log.e("ERROR", t.message ?: "Unknown error")
                }
            })
        }
    }

    private fun handleMonsterResponse(monsterResponse: MonsterResponse) {
        when {
            monsterResponse.fieldTypeDontSet() -> {
                showMessageDialog(
                    getString(R.string.warning),
                    getString(R.string.do_you_want_to_send_your_current_location),
                    positiveButtonLabel = getString(R.string.yes),
                    negativeButtonLabel = getString(R.string.no),
                    positiveButtonAction = {
                        startActivity(Intent(this@RadarActivity, LocationActivity::class.java))
                    }
                )
            }

            monsterResponse.isFieldClear() -> {
                Toast.makeText(this, "Sem digimons por perto.", Toast.LENGTH_SHORT).show()
            }

            monsterResponse.isFieldMonstersDontSet() -> {
                Toast.makeText(this, "Nenhum digimon cadastrado nesta área.", Toast.LENGTH_SHORT)
                    .show()
            }

            monsterResponse.isDigiviceNotCharged() -> {
                Toast.makeText(this, "Digivice sem carga no momento.", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Session.user?.let {
                    it.wallet += monsterResponse.coins
                }
                binding.layoutActionBar.viewWallet.text = "$ ${Session.user?.wallet}"

                monsterResponse.monster?.let { monster ->
                    ScannerFragment().apply {
                        wildId = monster.id
                        species = monster.species
                        image = monster.image
                        progress = Session.getScanProgress(monster)
                        coins = monsterResponse.coins
                    }.show(supportFragmentManager, "Scanner Completed")
                }
            }
        }
        checkDigiviceCharge()
    }

    private fun checkDigiviceCharge() {
        Session.user?.let { user ->
            val call = UserService().user().checkDigiviceCharge(user.id, user.digivice!!.id)

            call.enqueue(object : Callback<ChargeResponse?> {

                override fun onResponse(
                    call: Call<ChargeResponse?>,
                    response: Response<ChargeResponse?>
                ) {
                    response.body()?.let { chargeResponse ->
                        setupDigiviceCharge(chargeResponse.charge)
                    } ?: run {
                        Log.e("ERROR", response.errorBody()?.string() ?: "Unknown error")
                    }
                }

                override fun onFailure(call: Call<ChargeResponse?>, t: Throwable) {
                    Log.e("ERROR", t.message ?: "Unknown error")
                }
            })
        }
    }

    private fun setupDigiviceCharge(charge: Int) {
        Session.user?.digivice?.charge = charge
        binding.viewDigiviceCharge.progress = charge
        binding.viewDigivicePercent.text = "$charge%"
    }

    private fun setupActivity() {
        with(binding) {
            layoutActionBar.viewActivityTitle.text = getString(R.string.digi_radar)
            layoutActionBar.viewBackArrow.setOnClickListener { onBackPressed() }

            Session.user?.crest?.color?.let { color ->
                setStatusBarColor(color)
                layoutActionBar.viewActionBar.setCardBackgroundColor(Color.parseColor(color))
            }

            setupDigiviceInfos()

            if (Session.location!!.clear) {
                viewBossList.alpha = 0.2f
                viewBossList.text = "    Guardiões derrotados    "
                viewBossList.setOnClickListener(null)
            } else {
                viewBossList.setOnClickListener { guardianBattle() }
            }

            viewLocationTitle.text = Session.location?.title
        }
    }
}
