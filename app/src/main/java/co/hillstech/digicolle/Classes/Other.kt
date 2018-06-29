package co.hillstech.digicolle.Classes

class Other {
    var id: Int? = null
    var term: String? = null
    var value: String? = null

    constructor(): super(){}

    constructor(i: Int?, t: String?, v: String?){
        this.id = i
        this.term = t
        this.value = v
    }
}