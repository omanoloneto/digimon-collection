package co.hillstech.digicollection.ui.digiDex

import android.util.Log
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.services.apis.DexAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DigiDexPresenter {

    var view: View? = null
    private var monsterList: MutableList<Monster> = mutableListOf()

    fun getMonsterList() = monsterList

    fun requestMonsterList(){
        view?.showProgressRing()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                monsterList = DexAPI.requestMonsterList(Session.user!!.id).await()
                GlobalScope.launch(Dispatchers.Main) {
                    view?.inflateMonsterList()
                    view?.showTutorial()
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message)
            } finally {
                GlobalScope.launch(Dispatchers.Main) {
                    view?.hideProgressRing()
                }
            }
        }
    }

    interface View {
        fun inflateMonsterList()
        fun showTutorial()
        fun showProgressRing()
        fun hideProgressRing()
    }
}