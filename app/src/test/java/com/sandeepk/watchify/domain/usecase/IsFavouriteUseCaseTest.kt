package com.sandeepk.watchify.domain.usecase

import com.sandeepk.watchify.domain.repository.FavouriteMovieRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class IsFavouriteUseCaseTest {

    private val repository: FavouriteMovieRepository = mockk()
    private lateinit var useCase: IsFavouriteUseCase

    @Before
    fun setup() {
        useCase = IsFavouriteUseCase(repository)
    }

    @Test
    fun `invoke returns correct flow from repository`() = runTest {
        val movieId = 10
        val expectedFlow = flowOf(true)

        every { repository.isFavourite(movieId) } returns expectedFlow

        val result = useCase(movieId).first()

        assertTrue(result)
    }
}
