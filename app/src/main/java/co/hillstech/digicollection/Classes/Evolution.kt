package co.hillstech.digicollection.Classes

class Evolution {
    var pre: String? = null
    var evo: String? = null
    var lvl: Int = 0

    constructor(): super(){}

    constructor(p: String?, e: String?, l: Int){
        this.pre = p
        this.evo = e
        this.lvl = l
    }
}