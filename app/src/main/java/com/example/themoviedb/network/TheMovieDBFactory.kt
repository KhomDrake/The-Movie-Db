package com.example.themoviedb.network

import retrofit2.Retrofit

object TheMovieDBFactory {
    fun build(retrofit: Retrofit) : TheMovieDBApi {
        return retrofit.create(TheMovieDBApi::class.java)
    }
}