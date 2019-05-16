package co.hillstech.digicollection.enums

import android.content.Context
import co.hillstech.digicollection.R

enum class MonsterElement {
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK,
    LIGHT
}

fun MonsterElement.getElement(context: Context): String{
    return when(this){
        MonsterElement.NORMAL -> context.getString(R.string.normal)
        MonsterElement.FIGHTING -> context.getString(R.string.fighting)
        MonsterElement.FLYING -> context.getString(R.string.flying)
        MonsterElement.POISON -> context.getString(R.string.poison)
        MonsterElement.GROUND -> context.getString(R.string.ground)
        MonsterElement.ROCK -> context.getString(R.string.rock)
        MonsterElement.BUG -> context.getString(R.string.bug)
        MonsterElement.GHOST -> context.getString(R.string.ghost)
        MonsterElement.STEEL -> context.getString(R.string.steel)
        MonsterElement.FIRE -> context.getString(R.string.fire)
        MonsterElement.WATER -> context.getString(R.string.water)
        MonsterElement.GRASS -> context.getString(R.string.grass)
        MonsterElement.ELECTRIC -> context.getString(R.string.electric)
        MonsterElement.PSYCHIC -> context.getString(R.string.psychic)
        MonsterElement.ICE -> context.getString(R.string.ice)
        MonsterElement.DRAGON -> context.getString(R.string.dragon)
        MonsterElement.DARK -> context.getString(R.string.dark)
        MonsterElement.LIGHT -> context.getString(R.string.light)
    }
}
