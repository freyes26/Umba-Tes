package com.example.umba.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.umba.repository.database.model.Movie

@Dao
interface movieDao {
    @Query("SELECT * FROM movies ORDER BY original_title Desc")
    fun getMovie(): List<Movie>


    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieID(id :Int) : Movie

    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMovieCategory(category :Int) :  List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}