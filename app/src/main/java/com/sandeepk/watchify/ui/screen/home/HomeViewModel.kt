package com.sandeepk.watchify.ui.screen.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sandeepk.watchify.domain.model.Movie
import com.sandeepk.watchify.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _favourites = mutableStateMapOf<Int, Boolean>()
    val favourites: SnapshotStateMap<Int, Boolean> = _favourites

    val movies = getMoviesUseCase().cachedIn(viewModelScope)


    fun toggleFavourite(movie: Movie) {
        val current = _favourites[movie.id] ?: false
        _favourites[movie.id ?: 0] = !current

        // Optionally persist it
        // movieRepository.setFavourite(movie.id, !current)
    }

    fun isFavourite(movie: Movie): Boolean {
        return favourites[movie.id] == true
    }
}
