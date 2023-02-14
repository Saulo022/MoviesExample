package com.example.moviesexample.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesexample.api.RetroInstance
import com.example.moviesexample.api.TMDBService
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.create

class MovieViewModel : ViewModel() {
    lateinit var recyclerListLiveData: MutableLiveData<MovieList>

    init {
        recyclerListLiveData = MutableLiveData()
    }

    fun getRecyclerListObserver() : MutableLiveData<MovieList>{
        return recyclerListLiveData
    }

    fun makeApiCall(){
        viewModelScope.launch(Dispatchers.IO){
            val retroInstance = RetroInstance.getRetroInstance().create(TMDBService::class.java)
            val response = retroInstance.getPopularMovies("2ed8e60203b71ae90dfb88f9d3cd5101")
            recyclerListLiveData.postValue(response)
        }
    }
}


