package co.hillstech.digicolle.Retrofit

import co.hillstech.digicolle.models.UserResponse
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