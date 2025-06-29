package com.sandeepk.watchify.data.datasource

import com.sandeepk.watchify.data.local.dao.FavouriteMovieDao
import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavouriteMovieRepositoryImplTest {

    private val dao: FavouriteMovieDao = mockk()
    private lateinit var repository: FavouriteMovieRepositoryImpl

    @Before
    fun setup() {
        repository = FavouriteMovieRepositoryImpl(dao)
    }

    @Test
    fun `addFavourite calls dao insert`() = runTest {
        val movie = FavouriteMovieEntity(1, "Title", "Overview", "Poster")

        coEvery { dao.insert(movie) } just Runs

        repository.addFavourite(movie)

        coVerify { dao.insert(movie) }
    }

    @Test
    fun `removeFavourite calls dao deleteById`() = runTest {
        coEvery { dao.deleteById(1) } just Runs

        repository.removeFavourite(1)

        coVerify { dao.deleteById(1) }
    }

    @Test
    fun `getAllFavourites returns flow from dao`() = runTest {
        val mockFlow = flowOf(listOf(FavouriteMovieEntity(1, "Test", "Desc", "path")))
        every { dao.getAllFavourites() } returns mockFlow

        val result = repository.getAllFavourites().first()

        assertEquals(1, result.size)
        assertEquals("Test", result.first().title)
    }

    @Test
    fun `isFavourite returns flow from dao`() = runTest {
        // Arrange
        val movieId = 1
        val expectedFlow = flowOf(true)
        every { dao.isFavourite(movieId) } returns expectedFlow

        // Act
        val result = repository.isFavourite(movieId).first()

        // Assert
        assertEquals(true, result)
    }
}
