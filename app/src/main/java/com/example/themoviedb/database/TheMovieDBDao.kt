package com.example.themoviedb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedb.models.entity.TableFavorite
import com.example.themoviedb.models.entity.TableWish

@Dao
interface TheMovieDBDao {

    @Query("SELECT * FROM TableFavorite")
    fun favoriteList() : List<TableFavorite>

    @Query("SELECT * FROM TableWish")
    fun wishList() : List<TableWish>

    @Query("SELECT * FROM TableFavorite where idFavorite = :id")
    fun getFavorite(id: Int) : List<TableFavorite>

    @Query("SELECT * FROM TableWish where idWish = :id")
    fun getWish(id: Int) : List<TableWish>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(tableFavorite: TableFavorite)

    @Query("DELETE FROM TableFavorite where idFavorite = :id")
    fun deleteFavorite(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWish(tableWish: TableWish)

    @Query("DELETE FROM TableWish where idWish = :id")
    fun deleteWish(id: Int)
}