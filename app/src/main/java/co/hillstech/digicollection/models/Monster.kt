package co.hillstech.digicollection.models

import co.hillstech.digicollection.enums.MonsterAttribute
import co.hillstech.digicollection.enums.MonsterElement
import co.hillstech.digicollection.enums.MonsterPersonality

class Monster(
        var id: Int = 0,
        var species: String = "",
        var nick: String? = "",
        var experience: Int = 0,
        var personality: MonsterPersonality? = null,
        var attribute: MonsterAttribute? = null,
        var element: MonsterElement? = null,
        var image: String = "",
        var description: String = "",
        var progress: Int = 0,
        var type: Int = 0,
        var partner: Boolean = false,
        var base_hp: Float = 0f,
        var base_atk: Float = 0f,
        var base_def: Float = 0f,
        var base_sp_atk: Float = 0f,
        var base_sp_def: Float = 0f,
        var base_spd: Float = 0f,
        var scanned: Boolean = false,
        var locations: List<String> = listOf()
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