package co.hillstech.digicollection.models

class Event(
        val id: Int,
        val name: String,
        val description: String,
        val startDate: Int,
        val finishDate: Int,
        val isActive: Boolean
)