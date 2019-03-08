package co.hillstech.digicollection.models

class User(
        var id: Int,
        var name: String,
        var crest: Crest,
        var digivice: Digivice?,
        var wallet: Int,
        var partner: Monster,
        var scanList: MutableList<Monster>
) {}