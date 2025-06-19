package com.sandeepk.watchify.data.datasource

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    @SerializedName("dates")
    val dates: DatesDto,

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieDto>
)

data class DatesDto(
    @SerializedName("maximum")
    val maximum: String,

    @SerializedName("minimum")
    val minimum: String
)

data class MovieDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("overview")
    val overview: String? = null,
)
