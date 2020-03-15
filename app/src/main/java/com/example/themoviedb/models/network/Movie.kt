package com.example.themoviedb.models.network

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    val adult: Boolean
)