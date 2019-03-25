package co.hillstech.digicollection.ui.battleResult

import android.util.Log
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
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

    fun requestBattleResult() {
        view?.showProgressRing()
        Session.user?.let {
            val call = UserService().monster().getBattleResult(it.partner.id, Session.wild, it.id)

            call.enqueue(object : Callback<BattleResultViewModel?> {

                override fun onResponse(call: Call<BattleResultViewModel?>?,
                                        response: Response<BattleResultViewModel?>?) {
                    response?.body()?.let {

                        viewModel = it
                        updateUserWallet()

                        Session.user?.let {
                            it.partner.experience += viewModel.exp
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

    interface View {
        fun showBattleResults()
        fun showProgressRing()
        fun hideProgressRing()
        fun showSpotlights()
        fun updateViewWallet(newWallet: Int)
    }

}