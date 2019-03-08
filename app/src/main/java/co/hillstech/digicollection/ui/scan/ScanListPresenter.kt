package co.hillstech.digicollection.ui.scan

import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Monster

class ScanListPresenter {

    var view: ScanListPresenter.View? = null

    fun getScanList(): MutableList<Monster>{
        Session.user?.let{
            return it.scanList.apply {
                sortByDescending { it.progress }
            }
        } ?: run {
            return mutableListOf()
        }
    }

    interface View {

    }
}