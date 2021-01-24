package com.example.umba.repository.network

import com.example.umba.repository.database.model.Movie
import com.example.umba.repository.network.json.PageProperty
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RestApi {
    @GET("3/movie/popular?api_key=aa3afb3da8c72cf5353dedebdef807bf")
    suspend fun getProperties(): PageProperty?

    @GET("3/movie/latest?api_key=aa3afb3da8c72cf5353dedebdef807bf")
    suspend fun getLastest(): Movie

    @GET("3/movie/upcoming?api_key=aa3afb3da8c72cf5353dedebdef807bf")
    suspend fun getupcoming(): PageProperty?

    @GET
    fun getImage(@Url url: String):
            Call<ResponseBody>
}
