package co.hillstech.digicolle.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserService {
    private val retrofit = Retrofit.Builder()
            .baseUrl("http://digicolle.hillstech.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun newUser() = retrofit.create(Insert::class.java)
    fun login() = retrofit.create(Login::class.java)
    fun setLocation() = retrofit.create(Location::class.java)
}