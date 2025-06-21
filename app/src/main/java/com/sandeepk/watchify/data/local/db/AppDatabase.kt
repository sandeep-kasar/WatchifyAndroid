package com.sandeepk.watchify.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sandeepk.watchify.data.local.dao.FavouriteMovieDao
import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity

@Database(
    entities = [FavouriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteMovieDao(): FavouriteMovieDao
}
