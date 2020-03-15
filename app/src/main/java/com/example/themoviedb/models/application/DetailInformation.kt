package com.example.themoviedb.models.application

data class DetailInformation(
    val id: Int,
    val urlPoster: String?,
    val urlBackdrop: String?,
    val title: String,
    val year: String,
    val rate: String,
    val description: String,
    val adult: Boolean
)