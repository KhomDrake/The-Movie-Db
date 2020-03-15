package com.example.themoviedb.extension

import com.example.themoviedb.models.ImageInfoType
import com.example.themoviedb.models.application.DetailInformation
import com.example.themoviedb.models.entity.TableFavorite
import com.example.themoviedb.models.entity.TableWish
import com.example.themoviedb.models.network.Movie
import com.example.themoviedb.models.network.TV
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.models.network.TvDetail
import com.example.themoviedb.models.network.MovieDetail
import com.example.themoviedb.models.network.Season

fun Movie.toImageInfo() =
    ImageInfo(
        id, posterPath, adult, originalTitle
    )

fun List<Movie>.moviesToImagesInfos() : List<ImageInfo> {
    val images = mutableListOf<ImageInfo>()
    forEach { images.add(it.toImageInfo()) }
    return images.toList()
}

fun TV.toImageInfo() =
    ImageInfo(
        id, posterPath, adult = false, title = originalName
    )

fun List<TV>.tvsToImagesInfos() : List<ImageInfo> {
    val images = mutableListOf<ImageInfo>()
    forEach { images.add(it.toImageInfo()) }
    return images.toList()
}

fun Season.toImageInfo() = ImageInfo(
    id,
    posterPath,
    false,
    name
)

fun ImageInfo.toTableFavorite(imageInfoType: ImageInfoType) = TableFavorite(
    id,
    imageInfoType == ImageInfoType.MOVIE,
    posterPath,
    adult,
    title
)

fun ImageInfo.toTableWish(imageInfoType: ImageInfoType) = TableWish(
    id,
    imageInfoType == ImageInfoType.MOVIE,
    posterPath,
    adult,
    title
)

fun TableWish.toImageInfo() = ImageInfo(
    idWish,
    posterPath,
    adult,
    title,
    if (imageInfoType) ImageInfoType.MOVIE else ImageInfoType.TV
)

fun List<TableWish>.tableWishToImagesInfo() : List<ImageInfo> {
    val imagesInfo = mutableListOf<ImageInfo>()

    forEach { imagesInfo.add(it.toImageInfo()) }

    return imagesInfo
}

fun TableFavorite.toImageInfo() = ImageInfo(
    idFavorite,
    posterPath,
    adult,
    title,
    if (imageInfoType) ImageInfoType.MOVIE else ImageInfoType.TV
)

fun List<TableFavorite>.tableFavoriteToImagesInfo() : List<ImageInfo> {
    val imagesInfo = mutableListOf<ImageInfo>()

    forEach { imagesInfo.add(it.toImageInfo()) }

    return imagesInfo
}

fun DetailInformation.toImageInfo() = ImageInfo(
    id,
    urlPoster,
    adult,
    title
)

fun List<Season>.seasonsToImagesInfos() : List<ImageInfo> {
    val images = mutableListOf<ImageInfo>()
    forEach { images.add(it.toImageInfo()) }
    return images
}

fun TvDetail.toDetailInformation() = DetailInformation(
    id,
    posterPath,
    backdropPath,
    name,
    firstAirDate.split('-')[0],
    voteAverage.toString(),
    overview,
    false
)

fun MovieDetail.toDetailInformation() = DetailInformation(
    id,
    posterPath,
    backdropPath,
    title,
    releaseDate.split('-')[0],
    voteAverage.toString(),
    overview,
    adult
)