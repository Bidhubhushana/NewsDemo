package com.example.myapplication.manager
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Retrofit2Manager {

    private val mRetrofit: Retrofit
    private val client: OkHttpClient = OkHttpClient()
    private const val TIME_OUT: Long = 180

    init {

        val clientWith90sTimeout = client.newBuilder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        mRetrofit = Retrofit.Builder()
            .baseUrl(ApiEndPoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientWith90sTimeout)
            .build()


    }

    fun getRetrofit(): Retrofit = mRetrofit

}
