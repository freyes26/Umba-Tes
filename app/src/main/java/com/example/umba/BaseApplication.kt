package com.example.umba

import android.app.Application
import com.example.umba.repository.database.umbaDatabase
import com.example.umba.repository.network.ApiRetrofit
import com.example.umba.repository.network.Constants
import com.example.umba.repository.network.RestApi
import com.facebook.stetho.Stetho
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {
    companion object {
        lateinit var application: BaseApplication
    }

    private val retrofitConfig: Retrofit by lazy {  Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.retrofit.URL)
        .build()
    }

    private val imageRetrofitConfig : Retrofit by lazy { Retrofit.Builder()
        .baseUrl(Constants.retrofit.URL_IMAGE)
        .build()
    }

    val rest by lazy { ApiRetrofit(application.retrofitConfig).create(RestApi::class.java) }
    val db  by lazy { umbaDatabase.getDatabase(applicationContext) }

    val imageDownload by lazy { ApiRetrofit(application.imageRetrofitConfig).create(RestApi::class.java)}

    override fun onCreate() {
        super.onCreate()
        application=this
        Stetho.initializeWithDefaults(this);
    }
}