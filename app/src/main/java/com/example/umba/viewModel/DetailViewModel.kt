package com.example.umba.viewModel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.umba.BaseApplication
import com.example.umba.repository.database.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(val id : Int, application: BaseApplication) : AndroidViewModel(application) {

    private var _movie = MutableLiveData<Movie>()
    val movie get() : LiveData<Movie> = _movie

    init {
       getMovies(id)
    }

    private fun getMovies(id : Int){
        viewModelScope.launch {
            _movie.value = withContext(Dispatchers.IO) {
                BaseApplication.application.db.movieDao().getMovieID(id)
            }
        }
    }
}