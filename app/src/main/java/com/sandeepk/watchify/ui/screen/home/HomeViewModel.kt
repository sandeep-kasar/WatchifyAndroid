package com.sandeepk.watchify.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sandeepk.watchify.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    val movies = getMoviesUseCase().cachedIn(viewModelScope)
}
