package co.hillstech.digicollection.services.apis

import co.hillstech.digicollection.models.Event
import co.hillstech.digicollection.services.ServiceAPI
import co.hillstech.digicollection.services.interfaces.EventInterface
import kotlinx.coroutines.Deferred

object EventAPI {
    private val service by lazy {
        ServiceAPI.retrofit.create(EventInterface::class.java)
    }

    fun requestEventList(userId: Int): Deferred<MutableList<Event>> = EventAPI.service.requestEventList(userId)

}