package co.hillstech.digicollection.models

import co.hillstech.digicollection.enums.MonsterLevel

class Digivice(
        var id: Int,
        var model: String,
        var price: Int,
        var charge: Int,
        var cooldown: Int,
        var image: String,
        var resume: String,
        var maxLevel: MonsterLevel,
        var bought: Boolean,
        var equiped: Boolean
)