package com.example.moviesexample.data

data class MovieList(
    val page: Int,
    val results: MutableList<Movie>,
    val total_pages: Int,
    val total_results: Int
)