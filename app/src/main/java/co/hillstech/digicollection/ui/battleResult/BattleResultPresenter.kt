package co.hillstech.digicollection.ui.battleResult

import android.util.Log
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.BooleanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BattleResultPresenter {

    var viewModel = BattleResultViewModel()
    var view: BattleResultPresenter.View? = null

    fun getBattleResults() = viewModel

    fun updateUserWallet() {
        val coinsReceived = viewModel.coins
        Session.user?.let {
            it.wallet += coinsReceived
            view?.updateViewWallet(it.wallet)
        }
    }

    fun isBossBattle(): Boolean {
        return Session.guardians.size > 0
    }

    fun getNextBoss(): Int {
        val boss = Session.guardians[0]
        Session.guardians.removeAt(0)

        return boss.id
    }

    fun requestBattleResult() {
        val isBossBattle = isBossBattle()
        view?.showProgressRing()
        Session.user?.let {

            val call =
                    if (isBossBattle) UserService().monster().getBossBattleResult(it.partner.id, getNextBoss(), it.id)
                    else UserService().monster().getBattleResult(it.partner.id, Session.wild, it.id)

            call.enqueue(object : Callback<BattleResultViewModel?> {

                override fun onResponse(call: Call<BattleResultViewModel?>?,
                                        response: Response<BattleResultViewModel?>?) {
                    response?.body()?.let {

                        viewModel = it
                        updateUserWallet()

                        Session.user?.let {
                            it.partner.experience += viewModel.exp
                        }

                        if (it.result == BattleResultEnum.WIN && isBossBattle) {
                            if (isBossBattle()) {
                                view?.showNextButton()
                            } else {
                                unlockNextLocation()
                                view?.showWinMessage()
                            }
                        } else if (isBossBattle) {
                            view?.showLoseMessage()
                        }

                        view?.showBattleResults()
                        view?.hideProgressRing()
                        view?.showSpotlights()

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                        view?.hideProgressRing()
                    }
                }

                override fun onFailure(call: Call<BattleResultViewModel?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                    view?.hideProgressRing()
                }
            })
        }
    }

    fun unlockNextLocation() {
        Session.user?.let {

            val call = UserService().location().unlockNextLocation(Session.location!!.id, it.id)

            call.enqueue(object : Callback<BooleanResponse?> {

                override fun onResponse(call: Call<BooleanResponse?>?,
                                        response: Response<BooleanResponse?>?) {
                    response?.body()?.let {

                        //code

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<BooleanResponse?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }

    interface View {
        fun showBattleResults()
        fun showProgressRing()
        fun hideProgressRing()
        fun showSpotlights()
        fun showNextButton()
        fun showWinMessage()
        fun showLoseMessage()
        fun updateViewWallet(newWallet: Int)
    }

}