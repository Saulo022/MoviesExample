package com.example.moviesexample.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesexample.api.RetroInstance
import com.example.moviesexample.api.TMDBService
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import com.example.moviesexample.paging.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.create
import javax.inject.Inject


class MovieViewModel : ViewModel() {

    lateinit var recyclerListLiveData: MutableLiveData<MovieList>

    lateinit var movieList : MutableList<Movie>

    lateinit var movies: Movie


    private val _id = MutableLiveData<Int>()
    val id : LiveData<Int> = _id

    var tmdbService: TMDBService

    init {
        tmdbService = RetroInstance.getRetroInstance().create(TMDBService::class.java)
        recyclerListLiveData = MutableLiveData()
    }

    fun getRecyclerListObserver() : MutableLiveData<MovieList>{
        return recyclerListLiveData
    }

    /*
    fun makeApiCall(){
        viewModelScope.launch(Dispatchers.IO){
            val retroInstance = RetroInstance.getRetroInstance().create(TMDBService::class.java)
            val response = retroInstance.getPopularMovies("2ed8e60203b71ae90dfb88f9d3cd5101")
            recyclerListLiveData.postValue(response)
            movieList = response.results

            Log.d(ContentValues.TAG, " Hola $movieList")
        }
    }
     */

    fun saveId(idMovie: Int){
        _id.value = idMovie
    }

    fun saveMovie(movie: Movie){
        movies = movie
    }

    val getAllMovies = Pager(PagingConfig(pageSize = 1)){
        MoviePagingSource(tmdbService)
    }.flow.cachedIn(viewModelScope)
}


