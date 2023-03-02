package com.example.moviesexample.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.moviesexample.data.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movie: Movie)


    @Query("SELECT * FROM popular_movies")
    suspend fun getAll() : MutableList<Movie>

    @Update
    suspend fun updateFavMovies(movie: Movie)


    @Query("SELECT * FROM popular_movies WHERE video LIKE :video")
    suspend fun getFavMovies(video: Boolean): MutableList<Movie>
}
