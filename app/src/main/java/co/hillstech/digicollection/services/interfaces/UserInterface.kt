package co.hillstech.digicollection.services.interfaces

import co.hillstech.digicollection.Retrofit.CreateClass
import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.models.Monster
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInterface {
    @GET("v2/user/getUserMonsters/")
    fun getUserMonsters(@Query("user") userId: Int): Deferred<MutableList<Monster>>

    @GET("v2/user/updateBuddy/")
    fun updateBuddy(@Query("monster") monsterId: Int,
                    @Query("user") userId: Int): Deferred<BooleanResponse>

    @GET("v2/user/insert/")
    fun createAccount(@Query("name") name: String,
                      @Query("password") password: String): Deferred<CreateClass>
}