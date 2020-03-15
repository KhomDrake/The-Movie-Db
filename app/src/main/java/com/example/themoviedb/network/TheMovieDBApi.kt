package com.example.themoviedb.network

import com.example.themoviedb.models.network.TvDetail
import com.example.themoviedb.models.network.Movie
import com.example.themoviedb.models.network.MovieDetail
import com.example.themoviedb.models.network.Result
import com.example.themoviedb.models.network.Credit
import com.example.themoviedb.models.network.TV
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBApi {

    // movies
    @GET("movie/latest")
    fun latestMovieAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en"
    ): Deferred<Movie>

    @GET("movie/now_playing")
    fun nowPlayingMovieAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en",
        @Query("page") p: Int = 1
    ): Deferred<Result<Movie>>

    @GET("movie/popular")
    fun popularMovieAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en",
        @Query("page") p: Int = 1
    ): Deferred<Result<Movie>>

    @GET("movie/top_rated")
    fun topRatedMovieAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en",
        @Query("page") p: Int = 1
    ): Deferred<Result<Movie>>

    @GET("movie/upcoming")
    fun upcomingMovieAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en",
        @Query("page") p: Int = 1
    ): Deferred<Result<Movie>>

    @GET("movie/{id_movie}")
    fun movieDetailAsync(
        @Path("id_movie") idMovie: Int,
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145"
    ) : Deferred<MovieDetail>

    @GET("movie/{id_movie}/recommendations")
    fun movieRecommendationsAsync(
        @Path("id_movie") idMovie: Int,
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145"
    ) : Deferred<Result<Movie>>

    @GET("movie/{id_movie}/credits")
    fun movieCreditAsync(
        @Path("id_movie") idMovie: Int,
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145"
    ) : Deferred<Credit>

    // tv
    @GET("tv/latest")
    fun latestTvAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en"
    ) : Deferred<TV>

    @GET("tv/airing_today")
    fun tvAiringTodayTvAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en",
        @Query("page") p: Int = 1
    ) : Deferred<Result<TV>>

    @GET("tv/on_the_air")
    fun tvOnTheAirTvAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en",
        @Query("page") p: Int = 1
    ) : Deferred<Result<TV>>

    @GET("tv/popular")
    fun popularTvAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en",
        @Query("page") p: Int = 1
    ) : Deferred<Result<TV>>

    @GET("tv/top_rated")
    fun topRatedTvAsync(
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en",
        @Query("page") p: Int = 1
    ) : Deferred<Result<TV>>

    @GET("tv/{id_tv}")
    fun tvDetailAsync(
        @Path("id_tv") idTv: Int,
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en"
    ) : Deferred<TvDetail>

    @GET("tv/{id_tv}/credits")
    fun tvCreditAsync(
        @Path("id_tv") idTv: Int,
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en"
    ) : Deferred<Credit>

    @GET("tv/{id_tv}/similar")
    fun tvSimilarAsync(
        @Path("id_tv") idTv: Int,
        @Query("api_key") key: String = "ed84e9c8c38d4d0a8f3adaa5ba324145",
        @Query("language") language: String = "en"
    ) : Deferred<Result<TV>>
}