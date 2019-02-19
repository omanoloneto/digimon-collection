package co.hillstech.digicolle.models

class UserResponse(
        var status: Boolean,
        var message: String,
        var data: User
) { }