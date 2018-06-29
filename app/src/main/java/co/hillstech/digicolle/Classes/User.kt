package co.hillstech.digicolle.Classes

import co.hillstech.digicolle.R

class User {
    var name: String? = null
    var crest: String? = null

    constructor(): super(){}

    constructor(name: String?,crest: String?){
        this.name = name
        this.crest = crest
    }

    companion object {
        fun getDigiviceDrawable(num: Int): Int{
            if(num == 1) return R.drawable.digivice_01_01
            else if(num == 2) return R.drawable.digivice_01_02
            else if(num == 3) return R.drawable.digivice_01_03
            else if(num == 4) return R.drawable.digivice_01_04
            else if(num == 5) return R.drawable.digivice_01_05
            else if(num == 6) return R.drawable.digivice_01_06
            else if(num == 7) return R.drawable.digivice_01_07
            else if(num == 8) return R.drawable.digivice_01_08
            else if(num == 9) return R.drawable.digivice_01_09
            else if(num == 10) return R.drawable.digivice_01_10
            else if(num == 11) return R.drawable.digivice_01_11
            else return R.drawable.digivice_01_00
        }
        fun getDigiviceColor(num: Int): String{
            if(num == 1) return "#ff6600"
            else if(num == 2) return "#0000ff"
            else if(num == 3) return "#ff0000"
            else if(num == 4) return "#800080"
            else if(num == 5) return "#008000"
            else if(num == 6) return "#808080"
            else if(num == 7) return "#ffcc00" //ffff00
            else if(num == 8) return "#ffd5d5"
            else if(num == 9) return "#ff00cc"
            else if(num == 10) return "#ffcc00"
            else if(num == 11) return "#0000aa"
            else return "#2b2b2b"
        }
    }
}