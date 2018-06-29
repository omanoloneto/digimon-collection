package co.hillstech.digicolle.Classes

import android.icu.text.SimpleDateFormat
import java.util.*

class Codes {
    var monster: String? = null
    var friend: String? = null
    var date: Int = 0

    constructor(): super(){}

    constructor(m: String?, f: String?, d: Int){
        this.monster = m
        this.friend = f
        this.date = d
    }

    constructor(m: String?, f: String?){
        this.friend = f
        this.monster = m
        var today = Calendar.getInstance().time.year.toString()+
                    Calendar.getInstance().time.month.toString()+
                    Calendar.getInstance().time.day.toString()
        this.date = Integer.parseInt(today)
    }
}