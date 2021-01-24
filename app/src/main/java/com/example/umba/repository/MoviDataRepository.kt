package com.example.umba.repository

import android.util.Log
import com.example.umba.BaseApplication
import com.example.umba.repository.database.model.Movie

class MoviDataRepository():MoviRepository {
    override suspend fun getProperties(category: Int): List<Movie>? {
        return when (category) {
            1 -> {
                val movie = BaseApplication.application.rest.getLastest()
                Log.d("Data", "${movie.title}")
                listOf(movie)
            }
            2->  BaseApplication.application.rest.getProperties()?.results
            else -> BaseApplication.application.rest.getupcoming()?.results
        }
    }
}

class MovieBaseDataRepository():MoviRepository{
    override suspend fun getProperties(category: Int): List<Movie>? {
        return BaseApplication.application.db.movieDao().getMovieCategory(category)
    }
}
