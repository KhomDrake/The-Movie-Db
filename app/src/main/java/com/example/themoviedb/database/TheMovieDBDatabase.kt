package com.example.themoviedb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themoviedb.models.entity.TableFavorite
import com.example.themoviedb.models.entity.TableWish

@Database(entities = [
    TableFavorite::class,
    TableWish::class
], version = 1, exportSchema = false)
abstract class TheMovieDBDatabase : RoomDatabase() {

    protected abstract fun dao() : TheMovieDBDao

    fun favoriteList() = dao().favoriteList()

    fun wishList() = dao().wishList()

    fun getFavorite(id: Int) = dao().getFavorite(id)

    fun getWish(id: Int) = dao().getWish(id)

    fun insertFavorite(tableFavorite: TableFavorite) = dao().insertFavorite(tableFavorite)

    fun insertWish(tableWish: TableWish) = dao().insertWish(tableWish)

    fun deleteWish(id: Int) = dao().deleteWish(id)

    fun deleteFavorite(id: Int) = dao().deleteFavorite(id)
}