package co.hillstech.digicollection.services

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class RetrofitService(private val baseUrl: String?, private val httpClient: OkHttpClient? = null, private val customGson: Gson? = null) {

    private val retrofit by lazy {
        build(baseUrl, httpClient, customGson)
    }

    fun <T> create(interfaceClass: KClass<T>): T where T : Any {
        return retrofit.create(interfaceClass.java)
    }

    @Suppress("NOTHING_TO_INLINE")
    companion object {

        inline fun build(baseUrl: String?, httpClient: OkHttpClient? = null, customGson: Gson? = null): Retrofit {
            if (baseUrl.isNullOrEmpty()) throw Exception("baseUrl undefined!")

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val timeout: Long = 20
            val timeUnit = TimeUnit.SECONDS

            val okHttpClient = httpClient ?: OkHttpClient.Builder()
                    .connectTimeout(timeout, timeUnit)
                    .readTimeout(timeout, timeUnit)
                    .writeTimeout(timeout, timeUnit)
                    .addInterceptor(interceptor)
                    .build()

            val converterFactory = if (customGson != null) GsonConverterFactory.create(customGson) else GsonConverterFactory.create()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(converterFactory)
                    .build()
        }

        inline fun <reified T> create(baseUrl: String?, httpClient: OkHttpClient? = null, customGson: Gson? = null): T {
            return build(baseUrl, httpClient, customGson).create(T::class.java) as T
        }

    }

}