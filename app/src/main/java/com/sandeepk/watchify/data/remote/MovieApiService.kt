package com.sandeepk.watchify.data.remote


import com.sandeepk.watchify.data.datasource.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): MovieResponseDto
}