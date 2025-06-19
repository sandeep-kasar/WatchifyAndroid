package com.sandeepk.watchify.domain.usecase

import androidx.paging.PagingData
import com.sandeepk.watchify.domain.model.Movie
import com.sandeepk.watchify.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> = repository.getPagedMovies()
}
