package co.hillstech.digicollection.enums

import android.content.Context
import co.hillstech.digicollection.R

enum class MonsterElement {
    FIRE,
    WATER,
    GRASS,
    ELETRIC,
    GROUND,
    FLY,
    DRAGON,
    METAL,
    DARK,
    LIGHT
}

fun MonsterElement.getElement(context: Context): String{
    return when(this){
        MonsterElement.FIRE -> context.getString(R.string.fire)
        MonsterElement.WATER -> context.getString(R.string.water)
        MonsterElement.GRASS -> context.getString(R.string.grass)
        MonsterElement.ELETRIC -> context.getString(R.string.eletric)
        MonsterElement.GROUND -> context.getString(R.string.ground)
        MonsterElement.FLY -> context.getString(R.string.fly)
        MonsterElement.DRAGON -> context.getString(R.string.dragon)
        MonsterElement.METAL -> context.getString(R.string.metal)
        MonsterElement.DARK -> context.getString(R.string.dark)
        MonsterElement.LIGHT -> context.getString(R.string.luz)
    }
}
