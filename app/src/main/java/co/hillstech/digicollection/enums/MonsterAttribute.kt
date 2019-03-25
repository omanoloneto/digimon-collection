package co.hillstech.digicollection.enums

import android.content.Context
import co.hillstech.digicollection.R

enum class MonsterAttribute {
    VIRUS,
    DATA,
    VACCINE
}

fun MonsterAttribute.getAttribute(context: Context): String{
    return when(this){
        MonsterAttribute.VIRUS -> context.getString(R.string.virus)
        MonsterAttribute.DATA -> context.getString(R.string.data)
        MonsterAttribute.VACCINE -> context.getString(R.string.vaccine)
    }
}