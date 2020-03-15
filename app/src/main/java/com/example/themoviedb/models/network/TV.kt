package com.example.themoviedb.models.network

import com.google.gson.annotations.SerializedName

data class TV(
    val id: Int,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("poster_path")
    val posterPath: String
)