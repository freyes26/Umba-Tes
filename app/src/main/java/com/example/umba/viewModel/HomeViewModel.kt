package com.example.umba.viewModel

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.umba.BaseApplication
import com.example.umba.fragment.adapter.HomeAdapter
import com.example.umba.repository.ValidatorFactory
import com.example.umba.repository.database.model.Movie
import com.example.umba.repository.network.Connetion
import com.example.umba.repository.network.DownloadFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(application: BaseApplication, private val category: String) : AndroidViewModel(application) {
    private val repository by lazy { ValidatorFactory() }
    private var _allMovies = MutableLiveData<List<Movie>>()
    val allMovies: LiveData<List<Movie>> get() = _allMovies
    private var homeAdapter: HomeAdapter

    init {
        _allMovies = MutableLiveData()
        homeAdapter = HomeAdapter()
    }

    fun getAdapter(): HomeAdapter = homeAdapter

    fun getMovies() {
        viewModelScope.launch {
            var result = withContext(Dispatchers.IO) {
                suspend { repository.getValidator(1)?.getProperties(category.toInt()) }.invoke()
            }
            if (result != null) {
                _allMovies.postValue(result)
            }
            if (Connetion.isConnected(getApplication())) {
                result = withContext(Dispatchers.IO) {
                    suspend { repository.getValidator(2)?.getProperties(category.toInt()) }.invoke()
                }
                if (result != null) {
                    for (item in result) {
                        Log.d("Data", "${item.title}")
                        withContext(Dispatchers.IO) {
                            item.poster_path?.let { download(it, "${item.id}_front.jpg") }
                        }
                        withContext(Dispatchers.IO) {
                            item.backdrop_path?.let { download(it, "${item.id}_back.jpg") }
                        }
                        item.category = category.toInt()
                        BaseApplication.application.db.movieDao().insert(item)
                    }
                    _allMovies.postValue(result)
                }
            }
        }
    }

    private fun download(data: String, name: String) {
        try {
            val result = DownloadFile.dwonload(data)
            result.body()?.let {
                DownloadFile.saveImage(getApplication(), name, it )
            }
        } catch (e: Exception) {
            Log.e("Download", "${e.message}")
        }
    }
}
