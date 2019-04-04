package co.hillstech.digicollection.models

import co.hillstech.digicollection.enums.MonsterAttribute
import co.hillstech.digicollection.enums.MonsterElement
import co.hillstech.digicollection.enums.MonsterPersonality

class Monster(
        var id: Int,
        var species: String,
        var nick: String?,
        var experience: Int,
        var personality: MonsterPersonality,
        var attribute: MonsterAttribute,
        var element: MonsterElement,
        var image: String,
        var description: String,
        var progress: Int,
        var type: Int,
        var partner: Boolean,
        var base_hp: Float,
        var base_atk: Float,
        var base_def: Float,
        var base_sp_atk: Float,
        var base_sp_def: Float,
        var base_spd: Float
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