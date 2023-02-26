package com.example.moviesexample.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesexample.data.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movie: Movie)


    @Query("SELECT * FROM popular_movies")
    suspend fun getAll() : MutableList<Movie>

}
