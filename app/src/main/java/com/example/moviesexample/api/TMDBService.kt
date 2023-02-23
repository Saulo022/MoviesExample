package com.example.moviesexample.api

import com.example.androidengineerassignmentsaulov2.utils.Constants
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET(Constants.END_POINT)
    suspend fun getPopularMoviesAll(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Response<MovieList>
}