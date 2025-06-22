package com.sandeepk.watchify.domain.usecase

import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import com.sandeepk.watchify.domain.repository.FavouriteMovieRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAllFavouritesUseCaseTest {

    private val repository: FavouriteMovieRepository = mockk()
    private lateinit var useCase: GetAllFavouritesUseCase

    @Before
    fun setup() {
        useCase = GetAllFavouritesUseCase(repository)
    }

    @Test
    fun `invoke returns flow of favourite movies from repository`() = runTest {
        // Arrange
        val fakeList = listOf(FavouriteMovieEntity(1, "Title", "Overview", "Poster"))
        every { repository.getAllFavourites() } returns flowOf(fakeList)

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Title", result.first().title)

        verify { repository.getAllFavourites() }
    }
}
