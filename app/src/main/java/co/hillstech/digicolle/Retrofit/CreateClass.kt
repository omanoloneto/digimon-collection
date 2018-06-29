package co.hillstech.digicolle.Retrofit

import co.hillstech.digicolle.Classes.User

class CreateClass {
    var status: String? = null
    var message: String? = null
    var data: List<User>? = null

    constructor(): super(){}

    constructor(status: String?,message: String?){
        this.status = status
        this.message = message
    }

    constructor(status: String?,message: String?,data: List<User>?){
        this.status = status
        this.message = message
        this.data = data
    }
}