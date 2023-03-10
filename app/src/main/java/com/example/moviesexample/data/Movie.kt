package com.example.moviesexample.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "popular_movies")
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    @PrimaryKey val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    var video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)