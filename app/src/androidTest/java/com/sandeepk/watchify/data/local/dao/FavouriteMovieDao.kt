package com.sandeepk.watchify.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sandeepk.watchify.data.local.db.AppDatabase
import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouriteMovieDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: FavouriteMovieDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.favouriteMovieDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private fun sampleMovie(id: Int = 1) = FavouriteMovieEntity(
        id = id,
        title = "Movie $id",
        overview = "Overview $id",
        posterPath = "path$id.jpg"
    )

    @Test
    fun insert_and_getAllFavourites_returnsInsertedMovie() = runTest {
        val movie = sampleMovie()
        dao.insert(movie)

        dao.getAllFavourites().test {
            val list = awaitItem()
            assertThat(list).containsExactly(movie)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun delete_removesMovieFromFavourites() = runTest {
        val movie = sampleMovie()
        dao.insert(movie)
        dao.delete(movie)

        dao.getAllFavourites().test {
            val list = awaitItem()
            assertThat(list).doesNotContain(movie)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun deleteById_removesCorrectMovie() = runTest {
        val movie1 = sampleMovie(1)
        val movie2 = sampleMovie(2)
        dao.insert(movie1)
        dao.insert(movie2)

        dao.deleteById(1)

        dao.getAllFavourites().test {
            val list = awaitItem()
            assertThat(list).containsExactly(movie2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun isFavourite_returnsTrueIfMovieExists() = runTest {
        val movie = sampleMovie()
        dao.insert(movie)

        dao.isFavourite(movie.id).test {
            assertThat(awaitItem()).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun isFavourite_returnsFalseIfMovieDoesNotExist() = runTest {
        dao.isFavourite(999).test {
            assertThat(awaitItem()).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
