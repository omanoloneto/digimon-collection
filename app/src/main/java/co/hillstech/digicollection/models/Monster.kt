package co.hillstech.digicollection.models

class Monster(
        var id: Int,
        var species: String,
        var experience: Int,
        var personality: String,
        var image: String,
        var progress: Int,
        var type: Int
) {
    fun getLevel():String{
        return when(this.type){
            1 -> "Fresh"
            2 -> "In-Training"
            3 -> "Rookie"
            4 -> "Champion"
            5 -> "Ultimate"
            6 -> "Mega"
            else -> ""
        }
    }
}