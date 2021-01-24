package com.example.umba.repository.network

import retrofit2.Retrofit

class ApiRetrofit(val rtrofit: Retrofit) {
    fun <T> create(value: Class<T>): T {
        return rtrofit.create(value)
    }
}