package co.hillstech.digicollection.services.interfaces

import co.hillstech.digicollection.models.Event
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface EventInterface {
    @GET("v2/events/getEvents/")
    fun requestEventList(@Query("user") userId: Int): Deferred<MutableList<Event>>
}