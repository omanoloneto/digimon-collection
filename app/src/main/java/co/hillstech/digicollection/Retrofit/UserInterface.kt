package co.hillstech.digicollection.Retrofit

import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.models.StoreResponse
import co.hillstech.digicollection.models.UserResponse
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
                 @Query("digivice") digivice: Int): Call<BooleanResponse>
}

interface Location {
    @GET("user/location/")
    fun exe(@Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("lonmin") lonmin: String,
            @Query("lonmax") lonmax: String,
            @Query("latmin") latmin: String,
            @Query("latmax") latmax: String,
            @Query("tipo") tipo: String): Call<CreateClass>
}