package com.example.moviesexample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesexample.data.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class TMDBDatabase : RoomDatabase() {

    abstract fun getMovieDao() : MovieDao
}