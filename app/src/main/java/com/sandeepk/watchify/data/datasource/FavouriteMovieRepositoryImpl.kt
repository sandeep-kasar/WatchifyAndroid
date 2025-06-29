package com.sandeepk.watchify.data.datasource

import com.sandeepk.watchify.data.local.dao.FavouriteMovieDao
import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import com.sandeepk.watchify.domain.repository.FavouriteMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteMovieRepositoryImpl @Inject constructor(
    private val dao: FavouriteMovieDao
) : FavouriteMovieRepository {
    override suspend fun addFavourite(movie: FavouriteMovieEntity) = dao.insert(movie)
    override suspend fun removeFavourite(movieId: Int) = dao.deleteById(movieId)
    override fun getAllFavourites(): Flow<List<FavouriteMovieEntity>> = dao.getAllFavourites()
    override fun isFavourite(movieId: Int): Flow<Boolean> = dao.isFavourite(movieId)
}
