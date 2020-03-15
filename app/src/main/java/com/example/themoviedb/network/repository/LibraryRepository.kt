package com.example.themoviedb.network.repository

import com.example.themoviedb.database.TheMovieDBDatabase
import com.example.themoviedb.extension.tableFavoriteToImagesInfo
import com.example.themoviedb.extension.tableWishToImagesInfo
import com.example.themoviedb.models.entity.TableFavorite
import com.example.themoviedb.models.entity.TableWish

class LibraryRepository(private val theMovieDBDatabase: TheMovieDBDatabase) {

    fun favorites() = theMovieDBDatabase.favoriteList().tableFavoriteToImagesInfo()

    fun wish() = theMovieDBDatabase.wishList().tableWishToImagesInfo()

    fun getFavorite(id: Int) = theMovieDBDatabase.getFavorite(id).tableFavoriteToImagesInfo()

    fun getWish(id: Int) = theMovieDBDatabase.getWish(id).tableWishToImagesInfo()

    fun insertFavorite(tableFavorite: TableFavorite) = theMovieDBDatabase.insertFavorite(tableFavorite)

    fun insertWish(tableWish: TableWish) = theMovieDBDatabase.insertWish(tableWish)

    fun deleteFavorite(id: Int) = theMovieDBDatabase.deleteFavorite(id)

    fun deleteWish(id: Int) = theMovieDBDatabase.deleteWish(id)
}