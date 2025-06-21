package com.sandeepk.watchify.data.local.dao

import androidx.room.*
import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: FavouriteMovieEntity)

    @Delete
    suspend fun delete(movie: FavouriteMovieEntity)

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    suspend fun deleteById(movieId: Int)

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavourites(): Flow<List<FavouriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_movies WHERE id = :movieId)")
    fun isFavourite(movieId: Int): Flow<Boolean>
}
