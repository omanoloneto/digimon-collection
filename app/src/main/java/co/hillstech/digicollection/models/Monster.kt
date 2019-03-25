package co.hillstech.digicollection.models

import co.hillstech.digicollection.enums.MonsterAttribute
import co.hillstech.digicollection.enums.MonsterElement

class Monster(
        var id: Int,
        var species: String,
        var nick: String?,
        var experience: Int,
        var personality: String,
        var attribute: MonsterAttribute,
        var element: MonsterElement,
        var image: String,
        var progress: Int,
        var type: Int,
        var partner: Boolean
) {
    fun getLevel():String{
        return when(this.type){
            1 -> "Fresh"
            2 -> "In-Training"
            3 -> "Rookie"
            4 -> "Champion"
            5 -> "Ultimate"
            6 -> "Mega"
            else -> ""
        }
    }
}