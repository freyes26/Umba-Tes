package com.example.umba.repository

import com.example.umba.repository.database.model.Movie

interface MoviRepository {
   suspend fun getProperties(category : Int): List<Movie>?
}