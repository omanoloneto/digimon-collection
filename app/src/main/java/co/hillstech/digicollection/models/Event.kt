package co.hillstech.digicollection.models

class Event(
        val id: Int,
        val name: String,
        val description: String,
        val startDate: String,
        val finishDate: String,
        val isActive: Boolean
)