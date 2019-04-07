package co.hillstech.digicollection.models

class Location(
        var id: Int,
        var title: String,
        var unlocked: Boolean,
        var clear: Boolean,
        var monsters: List<Monster>,
        var bosses: List<Monster>
)