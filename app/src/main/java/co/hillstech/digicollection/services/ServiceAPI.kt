package co.hillstech.digicollection.services

import co.hillstech.digicollection.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ServiceAPI {
    val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val timeout: Long = 20
        val timeUnit = TimeUnit.SECONDS

        val httpClient = OkHttpClient.Builder()
                .connectTimeout(timeout, timeUnit)
                .readTimeout(timeout, timeUnit)
                .writeTimeout(timeout, timeUnit)
                .addInterceptor(interceptor)
                .build()

        RetrofitService.build(BuildConfig.HTTP_SERVER, httpClient)
    }
}