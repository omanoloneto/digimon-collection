package co.hillstech.digicollection.models

class UserResponse(
        var status: Boolean,
        var message: String,
        var data: User
) {}