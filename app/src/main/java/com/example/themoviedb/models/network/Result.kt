package com.example.themoviedb.models.network

import com.google.gson.annotations.SerializedName

data class Result<T>(
    val results: List<T>,
    val page: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)