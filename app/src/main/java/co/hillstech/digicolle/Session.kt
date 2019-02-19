package co.hillstech.digicolle

import co.hillstech.digicolle.Classes.Monster
import co.hillstech.digicolle.models.User

class Session {
    companion object {
        var code: String? = null
        var specie: String? = null
        var mysim: String? = null
        var monsters: List<Monster> = listOf()
        var seens: List<String> = listOf()
        lateinit var user: User
        var color: String? = null
    }
}