package com.example.moviesexample.data

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Room
import com.example.androidengineerassignmentsaulov2.utils.Constants
import com.example.moviesexample.api.TMDBService
import com.example.moviesexample.app.MyApp
import com.example.moviesexample.db.MovieDao
import com.example.moviesexample.db.TMDBDatabase
import com.example.moviesexample.network.NetworkBoundResource
import com.example.moviesexample.network.Resource
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Inject

class MovieRepository {

    private lateinit var tmdbService: TMDBService
    private lateinit var movieDao : MovieDao

    init {
        var tmdbDatabase: TMDBDatabase = Room.databaseBuilder(
            MyApp.instance,
            TMDBDatabase::class.java,
            "db_movies"
        ).build()

        val okHttpClientBuilder: OkHttpClient.Builder =
            OkHttpClient.Builder().addInterceptor(RequestInterceptor())
        val client: OkHttpClient = okHttpClientBuilder.build()
        movieDao = tmdbDatabase.getMovieDao()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        tmdbService = retrofit.create(TMDBService::class.java)
    }
}

