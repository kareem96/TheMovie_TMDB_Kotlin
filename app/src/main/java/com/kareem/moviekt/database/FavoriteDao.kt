package com.kareem.moviekt.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favoritetable")
    fun getAllFav(): List<Favorite>

    @Query("DELETE FROM favoritetable WHERE movieidcol = :id")
    fun delete(id: String)

    @Insert
    fun insertRow(favorite: Favorite)

    @Query("SELECT * FROM favoritetable where movieidcol = :id ")
    fun isFavorite(id: String): Favorite
}