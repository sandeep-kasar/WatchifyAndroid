package com.sandeepk.watchify.di

import com.sandeepk.watchify.data.datasource.FavouriteMovieRepositoryImpl
import com.sandeepk.watchify.data.datasource.MovieRepositoryImpl
import com.sandeepk.watchify.domain.repository.FavouriteMovieRepository
import com.sandeepk.watchify.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindFavouriteMovieRepository(
        impl: FavouriteMovieRepositoryImpl
    ): FavouriteMovieRepository
}
