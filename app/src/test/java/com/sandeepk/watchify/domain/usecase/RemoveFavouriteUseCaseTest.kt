package com.sandeepk.watchify.domain.usecase

import com.sandeepk.watchify.domain.repository.FavouriteMovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoveFavouriteUseCaseTest {

    private val repository: FavouriteMovieRepository = mockk(relaxed = true)
    private lateinit var useCase: RemoveFavouriteUseCase

    @Before
    fun setup() {
        useCase = RemoveFavouriteUseCase(repository)
    }

    @Test
    fun `invoke calls repository removeFavourite`() = runTest {
        // Arrange
        val movieId = 1

        // Act
        useCase(movieId)

        // Assert
        coVerify { repository.removeFavourite(movieId) }
    }
}
