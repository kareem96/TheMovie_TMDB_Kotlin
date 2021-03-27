package com.kareem.moviekt.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 2)
abstract class FavoriteDatabase:RoomDatabase() {
    abstract fun FavoriteDao(): FavoriteDao

    companion object {
        var Instance: FavoriteDatabase? = null
        fun getFavoriteDatabase(context: android.content.Context): FavoriteDatabase? {
            if (Instance == null) {
                synchronized(FavoriteDatabase::class) {
                    Instance =
                        Room.databaseBuilder(context, FavoriteDatabase::class.java, "myDB")
                            .allowMainThreadQueries().build()
                }
            }
            return Instance
        }
        fun destroyinstance(){
            Instance==null
        }
    }
}