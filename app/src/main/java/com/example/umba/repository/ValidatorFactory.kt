package com.example.umba.repository

class ValidatorFactory() {

    fun getValidator(selector : Int):MoviRepository? = when {
        isDataBase(selector) -> MovieBaseDataRepository()
        isRetrofit(selector) -> MoviDataRepository()
        else -> null
    }

    private fun isDataBase(selector : Int) = selector == 1
    private fun isRetrofit(selector : Int) = selector == 2
}