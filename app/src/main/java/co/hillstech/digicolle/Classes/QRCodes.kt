package co.hillstech.digicolle.Classes

class QRCodes {
    var id: Int = 0
    var name: String? = null
    var code: String? = null

    constructor() : super(){}

    constructor(id: Int, name: String, code: String){
        this.id = id
        this.name = name
        this.code = code
    }
}