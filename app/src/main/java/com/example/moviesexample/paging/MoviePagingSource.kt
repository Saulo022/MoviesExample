package com.example.moviesexample.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesexample.api.TMDBService
import com.example.moviesexample.data.Movie
import com.example.moviesexample.data.MovieList
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource constructor(private val tmdbService: TMDBService) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, Movie> {

        return try {
            val currentPage = params.key ?: 1
            val response = tmdbService.getPopularMoviesAll("2ed8e60203b71ae90dfb88f9d3cd5101",currentPage)
            val responseData = mutableListOf<Movie>()
            val data = response.body()?.results ?: emptyList()
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