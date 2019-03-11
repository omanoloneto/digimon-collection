package co.hillstech.digicollection.Retrofit

import co.hillstech.digicollection.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Insert {
    @GET("user/insert/")
    fun exe(@Query("name") name: String,
            @Query("password") password: String): Call<CreateClass>
}

interface Login {
    @GET("user/login/")
    fun exe(@Query("name") name: String,
            @Query("password") password: String): Call<UserResponse>
}

interface Store {
    @GET("store/getitems/")
    fun getItems(@Query("user") user: String): Call<StoreResponse>
}

interface User {
    @GET("user/updatewallet/")
    fun updateWallet(@Query("user") user: String,
                     @Query("wallet") wallet: Int): Call<BooleanResponse>

    @GET("user/updatedigivice/")
    fun updateDigivice(@Query("user") user: String,
                       @Query("digivice") digivice: Int,
                       @Query("equipe") equipe: Boolean): Call<BooleanResponse>

    @GET("user/checkdigivicecharge/")
    fun checkDigiviceCharge(@Query("user") user: Int,
                            @Query("digivice") digivice: Int): Call<ChargeResponse>

    @GET("user/updatescan/")
    fun updateScan(@Query("user") user: Int,
                   @Query("monster") monster: Int,
                   @Query("progress") progress: Int): Call<BooleanResponse>
}

interface Location {
    @GET("location/insert/")
    fun insert(@Query("lat") lat: String,
               @Query("lon") lon: String,
               @Query("lonmin") lonmin: String,
               @Query("lonmax") lonmax: String,
               @Query("latmin") latmin: String,
               @Query("latmax") latmax: String,
               @Query("tipo") tipo: String): Call<BooleanResponse>

    @GET("location/getinfectedmonster/")
    fun getInfectedMonster(@Query("lat") latitude: String,
                           @Query("lon") longitude: String,
                           @Query("digivice") digivice: Int,
                           @Query("user") user: Int): Call<MonsterResponse>
}