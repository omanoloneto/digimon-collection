package co.hillstech.digicollection.ui.evolutionList

import android.util.Log
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Monster
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvolutionListPresenter{

    var viewModel: MutableList<Monster> = mutableListOf()
    var view: EvolutionListPresenter.View? = null

    fun getEvolutionList() = viewModel

    fun requestEvolutionList() {
        view?.showProgressRing()
        Session.user?.let {
            val call = UserService().monster().getEvolutionLine(it.partner.id, it.id)

            call.enqueue(object : Callback<MutableList<Monster>?> {

                override fun onResponse(call: Call<MutableList<Monster>?>?,
                                        response: Response<MutableList<Monster>?>?) {
                    response?.body()?.let {

                        viewModel = it

                        view?.inflateEvolutionList()

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }

                    view?.hideProgressRing()
                }

                override fun onFailure(call: Call<MutableList<Monster>?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                    view?.hideProgressRing()
                }
            })
        }
    }

    interface View {
        fun inflateEvolutionList()
        fun refreshEvolutionList()
        fun showProgressRing()
        fun hideProgressRing()
    }
}