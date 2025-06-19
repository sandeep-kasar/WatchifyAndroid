package com.sandeepk.watchify.data.mappers

import com.sandeepk.watchify.data.datasource.MovieDto
import com.sandeepk.watchify.domain.model.Movie


fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview
    )
}
