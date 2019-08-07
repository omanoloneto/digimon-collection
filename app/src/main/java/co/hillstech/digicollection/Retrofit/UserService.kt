package co.hillstech.digicollection.Retrofit

import co.hillstech.digicollection.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserService {
    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val timeout: Long = 60
        val timeUnit = TimeUnit.SECONDS

        val httpClient = OkHttpClient.Builder()
                .connectTimeout(timeout, timeUnit)
                .readTimeout(timeout, timeUnit)
                .writeTimeout(timeout, timeUnit)
                .addInterceptor(interceptor)

        val baseUrl = BuildConfig.HTTP_SERVER + "v2/"

        Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun newUser() = retrofit.create(Insert::class.java)
    fun login() = retrofit.create(Login::class.java)
    fun store() = retrofit.create(Store::class.java)
    fun user() = retrofit.create(User::class.java)
    fun location() = retrofit.create(Location::class.java)
    fun monster() = retrofit.create(Monster::class.java)
}