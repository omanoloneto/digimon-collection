package co.hillstech.digicollection.ui.digiBank

import android.util.Log
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.models.Monster
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DigiBankPresenter {

    var viewModel: MutableList<Monster> = mutableListOf()
    var view: DigiBankPresenter.View? = null

    fun getDataBoxList() = viewModel

    fun requestDataBoxList() {
        view?.showProgressRing()
        Session.user?.let {
            val call = UserService().user().getUserMonsters(it.id)

            call.enqueue(object : Callback<MutableList<Monster>?> {

                override fun onResponse(call: Call<MutableList<Monster>?>?,
                                        response: Response<MutableList<Monster>?>?) {
                    response?.body()?.let {

                        viewModel = it

                        view?.inflateDataBoxList()

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

    fun changePartner(monster: Monster){
        view?.showProgressRing()
        Session.user?.let {
            val call = UserService().user().updateBuddy(monster.id, it.id)

            call.enqueue(object : Callback<BooleanResponse?> {

                override fun onResponse(call: Call<BooleanResponse?>?,
                                        response: Response<BooleanResponse?>?) {
                    response?.body()?.let {

                        updateUserBuddy(monster)

                        view?.refreshDataBoxList()
                        view?.changeBuddyMessage(monster.species, monster.image)

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }

                    view?.hideProgressRing()
                }

                override fun onFailure(call: Call<BooleanResponse?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                    view?.hideProgressRing()
                }
            })
        }
    }

    private fun updateUserBuddy(monster: Monster) {
        Session.user?.let {
            it.partner = monster
        }

        val partner = viewModel.indexOf(
                viewModel.find { it.partner == true }
        )

        viewModel[partner].partner = false

        val newPartner = viewModel.indexOf(
                viewModel.find { it.id == monster.id }
        )

        viewModel[newPartner].partner = true

    }

    fun rename(monster: Monster) {
        view?.showProgressRing()
        Session.user?.let {
            val call = UserService().monster().rename(monster.id, monster.nick!!, it.id)

            call.enqueue(object : Callback<BooleanResponse?> {

                override fun onResponse(call: Call<BooleanResponse?>?,
                                        response: Response<BooleanResponse?>?) {
                    response?.body()?.let {

                        if(it.status){
                            Log.e("SUCCESS", "Nick do digimon atualizado com sucesso!")
                        }else{
                            Log.e("ERROR", "Erro ao atualizar o nick do digimon.")
                        }

                    } ?: run {
                        Log.e("ERROR", response?.errorBody().toString())
                    }

                    view?.hideProgressRing()
                }

                override fun onFailure(call: Call<BooleanResponse?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                    view?.hideProgressRing()
                }
            })
        }
    }

    interface View {
        fun changeBuddyMessage(monster: String, image: String)
        fun inflateDataBoxList()
        fun refreshDataBoxList()
        fun showProgressRing()
        fun hideProgressRing()
    }
}