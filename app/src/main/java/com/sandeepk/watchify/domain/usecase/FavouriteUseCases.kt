package com.sandeepk.watchify.domain.usecase

import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import com.sandeepk.watchify.domain.repository.FavouriteMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class AddFavouriteUseCase @Inject constructor(
    private val repository: FavouriteMovieRepository
) {
    suspend operator fun invoke(movie: FavouriteMovieEntity) = repository.addFavourite(movie)
}

class RemoveFavouriteUseCase @Inject constructor(
    private val repository: FavouriteMovieRepository
) {
    suspend operator fun invoke(movieId: Int) = repository.removeFavourite(movieId)
}

class GetAllFavouritesUseCase @Inject constructor(
    private val repository: FavouriteMovieRepository
) {
    operator fun invoke(): Flow<List<FavouriteMovieEntity>> = repository.getAllFavourites()
}

class IsFavouriteUseCase @Inject constructor(
    private val repository: FavouriteMovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Boolean> = repository.isFavourite(movieId)
}
