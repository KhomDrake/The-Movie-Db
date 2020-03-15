package com.example.themoviedb.models.network

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val adult: Boolean,
    val id: Int,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)