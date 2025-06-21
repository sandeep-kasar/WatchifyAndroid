package com.sandeepk.watchify.domain.repository

import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteMovieRepository {
    suspend fun addFavourite(movie: FavouriteMovieEntity)
    suspend fun removeFavourite(movieId: Int)
    fun getAllFavourites(): Flow<List<FavouriteMovieEntity>>
    fun isFavourite(movieId: Int): Flow<Boolean>
}