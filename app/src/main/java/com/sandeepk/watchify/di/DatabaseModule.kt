package com.sandeepk.watchify.di

import android.content.Context
import androidx.room.Room
import com.sandeepk.watchify.data.local.dao.FavouriteMovieDao
import com.sandeepk.watchify.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "watchify_database"
        ).build()
    }

    @Provides
    fun provideFavouriteMovieDao(db: AppDatabase): FavouriteMovieDao =
        db.favouriteMovieDao()
}
