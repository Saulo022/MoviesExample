package com.example.moviesexample.paging

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.Room
import com.example.moviesexample.api.TMDBService
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import com.example.moviesexample.db.TMDBDatabase
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val tmdbService: TMDBService, context: Context) : PagingSource<Int, Movie>() {



    val room by lazy {
        Room.databaseBuilder(
            context , TMDBDatabase::class.java, "popular_movies"
        ).build()
    }

    private var movieFav = mutableListOf<Movie>()


    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, Movie> {



        return try {
            val currentPage = params.key ?: 1
            Log.d(ContentValues.TAG, "HOLAAAAAAAAA")
            val response = tmdbService.getPopularMoviesAll("2ed8e60203b71ae90dfb88f9d3cd5101",currentPage)
            val responseData = mutableListOf<Movie>()
            Log.d(ContentValues.TAG, "HOLAAAAAAAAA2")

            Log.d(ContentValues.TAG, "HOLAAAAAAAAA3")
            val data = response.body()?.results ?: emptyList()

            val movie = room.getMovieDao().getFavMovies(true)

            var num = 0
            var num2 = 0
            Log.d(ContentValues.TAG, "HOLAAAAAAAAA5" + movie.size)
            /*while(num <= 2){
                Log.d(ContentValues.TAG, "HOLAAAAAAAAA4")
                while (num2 <= movie.size){
                    if (data[num].id == movie[num2].id){
                        data[num].video = true
                    }
                    num2++
                }
                num++
            }

             */

            Log.d(ContentValues.TAG, "HOLAAAAAAAAA5" + data.size)
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }
}