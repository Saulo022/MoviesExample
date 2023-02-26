package com.example.moviesexample.db

object Localinjector {

    var tmdbDatabase: TMDBDatabase? = null

    fun injectDb(): TMDBDatabase? {
        return tmdbDatabase
    }
}