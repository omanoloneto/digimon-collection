package co.hillstech.digicollection.enums

import android.content.Context
import co.hillstech.digicollection.R

enum class MonsterLevel {
    FRESH,
    INTRAINING,
    ROOKIE,
    CHAMPION,
    ULTIMATE,
    MEGA
}

fun MonsterLevel.toString(context: Context): String{
    return when(this){
        MonsterLevel.FRESH -> context.getString(R.string.fresh)
        MonsterLevel.INTRAINING -> context.getString(R.string.intraining)
        MonsterLevel.ROOKIE -> context.getString(R.string.rookie)
        MonsterLevel.CHAMPION -> context.getString(R.string.champion)
        MonsterLevel.ULTIMATE -> context.getString(R.string.ultimate)
        MonsterLevel.MEGA -> context.getString(R.string.mega)
    }
}

fun MonsterLevel.toInt(): Int{
    return when(this){
        MonsterLevel.FRESH -> 1
        MonsterLevel.INTRAINING -> 2
        MonsterLevel.ROOKIE -> 3
        MonsterLevel.CHAMPION -> 4
        MonsterLevel.ULTIMATE -> 5
        MonsterLevel.MEGA -> 6
    }
}
