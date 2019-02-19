package co.hillstech.digicolle.models

class User(
        var id: Int,
        var name: String,
        var crest: Crest,
        var partner: Monster
) { }