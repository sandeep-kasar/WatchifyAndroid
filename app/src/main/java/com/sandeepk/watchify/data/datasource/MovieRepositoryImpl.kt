package com.sandeepk.watchify.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sandeepk.watchify.BuildConfig
import com.sandeepk.watchify.data.remote.MovieApiService
import com.sandeepk.watchify.data.remote.MoviePagingSource
import com.sandeepk.watchify.domain.model.Movie
import com.sandeepk.watchify.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApiService
) : MovieRepository {

    override fun getPagedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = { MoviePagingSource(api, BuildConfig.API_KEY) }
        ).flow
    }
}

