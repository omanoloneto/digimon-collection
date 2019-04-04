package co.hillstech.digicollection.enums

import android.content.Context
import co.hillstech.digicollection.R

enum class MonsterPersonality {
    CALM,
    NERVOUS,
    CONTROLLED,
    TRICKSTER,
    TIMID
}

fun MonsterPersonality.toString(context: Context): String{
    return when(this){
        MonsterPersonality.CALM -> context.getString(R.string.calm)
        MonsterPersonality.NERVOUS -> context.getString(R.string.nervous)
        MonsterPersonality.CONTROLLED -> context.getString(R.string.controlled)
        MonsterPersonality.TRICKSTER -> context.getString(R.string.trickster)
        MonsterPersonality.TIMID -> context.getString(R.string.timid)
    }
}