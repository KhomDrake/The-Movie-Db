package com.example.themoviedb.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TableWish(
    @PrimaryKey val idWish: Int,
    val imageInfoType: Boolean,
    val posterPath: String?,
    val adult: Boolean,
    val title: String? = null
)