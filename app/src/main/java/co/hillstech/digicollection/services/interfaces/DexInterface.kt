package co.hillstech.digicollection.services.interfaces

import co.hillstech.digicollection.models.Monster
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface DexInterface {
    @GET("v2/vdex/getDex/")
    fun requestMonsterList(@Query("user") userId: Int): Deferred<MutableList<Monster>>
}