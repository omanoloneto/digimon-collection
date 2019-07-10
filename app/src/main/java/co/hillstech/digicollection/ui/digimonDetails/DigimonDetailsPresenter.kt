package co.hillstech.digicollection.ui.digimonDetails

import co.hillstech.digicollection.Session
import co.hillstech.digicollection.models.Monster

class DigimonDetailsPresenter {

    var view: View? = null

    fun getMonster() = Session.digimon ?: Monster()

    interface View{

    }
}