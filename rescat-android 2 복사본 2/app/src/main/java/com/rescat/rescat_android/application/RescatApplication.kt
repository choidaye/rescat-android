package com.rescat.rescat_android.application

import android.app.Application
import com.rescat.rescat_android.Preference.RescatPreference
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.network.RescatNetworkInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RescatApplication : Application() {


    private val baseURL = "http://13.209.145.139:8080/"
    lateinit var networkService: NetworkService
    lateinit var retrofit : Retrofit


    companion object {
        lateinit var instance : RescatApplication
        lateinit var preference: RescatPreference
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        preference = RescatPreference(applicationContext)
        buildNetWork()
    }

    fun buildNetWork() {
        val retrofit: Retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .apply {
                        addInterceptor(RescatNetworkInterceptor())
                    }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
        networkService = retrofit.create(NetworkService::class.java)
    }
}