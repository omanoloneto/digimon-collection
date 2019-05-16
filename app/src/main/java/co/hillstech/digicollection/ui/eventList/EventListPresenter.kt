package co.hillstech.digicollection.ui.eventList

import android.util.Log
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.services.apis.EventAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventListPresenter {
    var viewModel = EventListViewModel()
    var view: EventListPresenter.View? = null

    fun getEventList() = viewModel.eventList

    fun requestEventList() {
        view?.showProgressRing()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                viewModel.eventList = EventAPI.requestEventList(Session.user!!.id).await()
                GlobalScope.launch(Dispatchers.Main) {
                    view?.inflateEventList()
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
        fun inflateEventList()
        fun showProgressRing()
        fun hideProgressRing()
    }
}