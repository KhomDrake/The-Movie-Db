package com.example.themoviedb.network.repository

import com.example.themoviedb.network.Api

class MovieRepository(private val api: Api) {

    suspend fun moviesNowPlaying() = api.moviesNowPlaying()

    suspend fun movieLatest() = api.moviesLatest()

    suspend fun movieDetail(idMovie: Int) = api.movieDetail(idMovie)

    suspend fun movieCast(idMovie: Int) = api.movieCast(idMovie)

    suspend fun similarMovies(idMovie: Int) = api.similarMovie(idMovie)

    suspend fun moviesPopular() = api.moviesPopular()

    suspend fun moviesTopRated() = api.moviesTopRated()

    suspend fun moviesUpcoming() = api.moviesUpcoming()
}