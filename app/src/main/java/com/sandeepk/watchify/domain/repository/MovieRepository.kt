package com.sandeepk.watchify.domain.repository

import androidx.paging.PagingData
import com.sandeepk.watchify.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPagedMovies(): Flow<PagingData<Movie>>
}