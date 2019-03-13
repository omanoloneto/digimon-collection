package co.hillstech.digicollection.models

class Digivice(
        var id: Int,
        var model: String,
        var price: Int,
        var charge: Int,
        var cooldown: Int,
        var image: String,
        var resume: String,
        var maxLevel: String,
        var bought: Boolean
) {}