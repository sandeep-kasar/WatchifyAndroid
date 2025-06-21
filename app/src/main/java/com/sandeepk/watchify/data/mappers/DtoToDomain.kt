package com.sandeepk.watchify.data.mappers

import com.sandeepk.watchify.data.datasource.MovieDto
import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import com.sandeepk.watchify.domain.model.Movie


fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview
    )
}

fun FavouriteMovieEntity.toMovie(): Movie = Movie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    posterPath = this.posterPath
)