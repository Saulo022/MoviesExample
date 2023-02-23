package com.example.moviesexample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesexample.api.RetroInstance
import com.example.moviesexample.api.TMDBService
import com.example.moviesexample.data.Movie
import com.example.moviesexample.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    lateinit var tmdbService: TMDBService

    init {
        tmdbService = RetroInstance.getRetroInstance().create(TMDBService::class.java)
    }

}