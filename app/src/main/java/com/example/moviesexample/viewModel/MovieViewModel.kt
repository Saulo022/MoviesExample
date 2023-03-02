package com.example.moviesexample.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviesexample.api.RetroInstance
import com.example.moviesexample.api.TMDBService
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import com.example.moviesexample.paging.MoviePagingSource


class MovieViewModel : ViewModel() {

    lateinit var recyclerListLiveData: MutableLiveData<MovieList>

    var movieFavList : MutableList<Int> = mutableListOf()
    //var movieFavList : MutableList<Int>? = null

    var movies: Movie? = null



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


    fun saveFavMovie(id:Int){
        movieFavList.add(id)
    }

    fun removeMovie(id: Int){
        movieFavList.remove(id)

    }


    fun saveId(idMovie: Int){
        _id.value = idMovie
    }


    fun saveMovie(movie: Movie){
        movies = movie
    }

    fun getAllMovies(context: Context) = Pager(PagingConfig(pageSize = 1)){
        MoviePagingSource(tmdbService,context)
    }.flow.cachedIn(viewModelScope)
}


