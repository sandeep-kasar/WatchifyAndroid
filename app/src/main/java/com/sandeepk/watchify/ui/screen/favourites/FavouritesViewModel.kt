package com.sandeepk.watchify.ui.screen.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import com.sandeepk.watchify.domain.model.Movie
import com.sandeepk.watchify.domain.usecase.AddFavouriteUseCase
import com.sandeepk.watchify.domain.usecase.GetAllFavouritesUseCase
import com.sandeepk.watchify.domain.usecase.IsFavouriteUseCase
import com.sandeepk.watchify.domain.usecase.RemoveFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val addFavouriteUseCase: AddFavouriteUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase,
    getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val isFavouriteUseCase: IsFavouriteUseCase
) : ViewModel() {

    val favourites: StateFlow<List<FavouriteMovieEntity>> = getAllFavouritesUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun removeFromFavourites(movieId: Int) {
        viewModelScope.launch {
            removeFavouriteUseCase(movieId)
        }
    }

    fun isFavourite(movieId: Int): StateFlow<Boolean> =
        isFavouriteUseCase(movieId)
            .map { it }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleFavourite(movie: Movie) {
        viewModelScope.launch {
            val currentFav = isFavouriteUseCase(movie.id ?: 0).first()
            if (currentFav) {
                removeFavouriteUseCase(movie.id ?: 0)
            } else {
                val entity = FavouriteMovieEntity(
                    id = movie.id ?: 0,
                    title = movie.title ?: "",
                    overview = movie.overview ?: "",
                    posterPath = movie.posterPath ?: ""
                )
                addFavouriteUseCase(entity)
            }
        }
    }
}
