package com.example.themoviedb.network

class Api(private val theMovieDBApi: TheMovieDBApi) {

    suspend fun moviesLatest() = theMovieDBApi.latestMovieAsync().await()

    suspend fun moviesNowPlaying() = theMovieDBApi.nowPlayingMovieAsync().await()

    suspend fun moviesPopular() = theMovieDBApi.popularMovieAsync().await()

    suspend fun moviesTopRated() = theMovieDBApi.topRatedMovieAsync().await()

    suspend fun moviesUpcoming() = theMovieDBApi.upcomingMovieAsync().await()

    suspend fun movieDetail(idMovie: Int) = theMovieDBApi.movieDetailAsync(idMovie = idMovie).await()

    suspend fun movieCast(idMovie: Int) = theMovieDBApi.movieCreditAsync(idMovie = idMovie).await()

    suspend fun similarMovie(idMovie: Int) = theMovieDBApi.movieRecommendationsAsync(idMovie = idMovie).await()

    suspend fun latestTv() = theMovieDBApi.latestTvAsync().await()

    suspend fun tvAiringToday() = theMovieDBApi.tvAiringTodayTvAsync().await()

    suspend fun tvOnTheAir() = theMovieDBApi.tvOnTheAirTvAsync().await()

    suspend fun popularTv() = theMovieDBApi.popularTvAsync().await()

    suspend fun topRatedTv() = theMovieDBApi.topRatedTvAsync().await()

    suspend fun tvDetail(idTv: Int) = theMovieDBApi.tvDetailAsync(idTv = idTv).await()

    suspend fun tvCast(idTv: Int) = theMovieDBApi.tvCreditAsync(idTv = idTv).await()

    suspend fun tvSimilar(idTv: Int) = theMovieDBApi.tvSimilarAsync(idTv = idTv).await()
}