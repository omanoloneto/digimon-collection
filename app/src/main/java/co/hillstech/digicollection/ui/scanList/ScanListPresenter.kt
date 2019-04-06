package co.hillstech.digicollection.ui.scanList

import android.util.Log
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Monster
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanListPresenter {

    var view: ScanListPresenter.View? = null

    fun getScanList(): MutableList<Monster> {
        Session.user?.let {
            return it.scanList.apply {
                sortByDescending { it.progress }
            }
        } ?: run {
            return mutableListOf()
        }
    }

    fun convertMonster(monster: Monster) {
        view?.showProgressRing()
        Session.user?.let {
            val call = UserService().monster().digiconvert(monster.id, it.id)

            call.enqueue(object : Callback<Monster?> {

                override fun onResponse(call: Call<Monster?>?,
                                        response: Response<Monster?>?) {
                    response?.body()?.let {

                        updateScanItem(it)

                        view?.convertMessage(monster.species, monster.image)

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }

                    view?.hideProgressRing()
                }

                override fun onFailure(call: Call<Monster?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                    view?.hideProgressRing()
                }
            })
        }
    }

    private fun updateScanItem(monster: Monster) {
        Session.user?.let {
            val index = it.scanList.indexOf(
                    it.scanList.find { it.id == monster.id }
            )

            it.scanList[index].progress = monster.progress

            view?.refreshScanList()
        }
    }

    interface View {
        fun convertMessage(monster: String, image: String)
        fun refreshScanList()
        fun showProgressRing()
        fun hideProgressRing()
    }
}