package com.example.moviesexample.datasource

import com.example.moviesexample.data.MovieList
import retrofit2.Response

interface MovieRemoteDataSource {

    suspend fun getMovies() : Response<MovieList>
}