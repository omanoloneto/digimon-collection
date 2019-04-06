package co.hillstech.digicollection.ui.digiviceList

import android.util.Log
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Digivice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DigiviceListPresenter {

    var viewModel = DigiviceListViewModel()
    var view: DigiviceListPresenter.View? = null

    fun getDigiviceList() = viewModel.digiviceList

    fun changeEquipedDigivice(digiviceId: Int) {
        view?.showProgressRing()
        Session.user?.let {
            val call = UserService().user().changeEquipedDigivice(it.id, digiviceId)

            call.enqueue(object : Callback<MutableList<Digivice>?> {

                override fun onResponse(call: Call<MutableList<Digivice>?>?,
                                        response: Response<MutableList<Digivice>?>?) {
                    response?.body()?.let {

                        viewModel.digiviceList = it
                        view?.refreshEquipedDigivice(
                                it.find { it.equiped }
                        )
                        view?.inflateDigiviceList()

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }

                    view?.hideProgressRing()
                }

                override fun onFailure(call: Call<MutableList<Digivice>?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                    view?.hideProgressRing()
                }
            })
        }
    }

    fun requestDigiviceList() {
        view?.showProgressRing()
        Session.user?.let {
            val call = UserService().user().getUserDevices(it.id)

            call.enqueue(object : Callback<MutableList<Digivice>?> {

                override fun onResponse(call: Call<MutableList<Digivice>?>?,
                                        response: Response<MutableList<Digivice>?>?) {
                    response?.body()?.let {

                        viewModel.digiviceList = it
                        view?.inflateDigiviceList()

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }

                    view?.hideProgressRing()
                }

                override fun onFailure(call: Call<MutableList<Digivice>?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                    view?.hideProgressRing()
                }
            })
        }
    }

    interface View {
        fun refreshEquipedDigivice(digivice: Digivice?)
        fun changeEquipedDigivice(digiviceId: Int)
        fun inflateDigiviceList()
        fun refreshDigiviceList()
        fun showProgressRing()
        fun hideProgressRing()
    }
}