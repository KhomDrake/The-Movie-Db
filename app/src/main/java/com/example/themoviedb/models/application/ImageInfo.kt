package com.example.themoviedb.models.application

import com.example.themoviedb.models.ImageInfoType
import com.example.themoviedb.models.application.ImageInfo.Companion.imgNotFound
import com.example.themoviedb.models.application.ImageInfo.Companion.plus18
import com.example.themoviedb.models.application.ImageInfo.Companion.urlImg

data class ImageInfo(
    val id: Int,
    val posterPath: String?,
    val adult: Boolean,
    val title: String? = null,
    val imageInfoType: ImageInfoType? = null
) {
    companion object {
        const val urlImg = "https://image.tmdb.org/t/p/w300"
        const val imgNotFound = "https://rentzzz.com/images/img_no_found.png"
        const val plus18 = "https://diarionews.com.br/wp-content/uploads/2018/02/18-logo-1.png"
    }
}

fun ImageInfo.createUrl() = when {
    posterPath == null -> imgNotFound
    adult -> plus18
    else -> urlImg + posterPath
}