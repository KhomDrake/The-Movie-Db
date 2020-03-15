package com.example.themoviedb.extension

import com.example.themoviedb.models.application.ImageInfo

fun String?.path() : String {
    return if(this != null) ImageInfo.urlImg + this else ImageInfo.imgNotFound
}