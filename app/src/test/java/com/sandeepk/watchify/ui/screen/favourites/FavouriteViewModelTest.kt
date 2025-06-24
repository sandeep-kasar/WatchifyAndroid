package com.sandeepk.watchify.ui.screen.favourites

import app.cash.turbine.test
import com.sandeepk.watchify.StandardTestDispatcherRule
import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import com.sandeepk.watchify.domain.model.Movie
import com.sandeepk.watchify.domain.usecase.AddFavouriteUseCase
import com.sandeepk.watchify.domain.usecase.GetAllFavouritesUseCase
import com.sandeepk.watchify.domain.usecase.IsFavouriteUseCase
import com.sandeepk.watchify.domain.usecase.RemoveFavouriteUseCase
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavouriteViewModelTest {

    @get:Rule
    val dispatcherRule = StandardTestDispatcherRule()

    @MockK(relaxed = true)
    lateinit var addUseCase: AddFavouriteUseCase

    @MockK(relaxed = true)
    lateinit var removeUseCase: RemoveFavouriteUseCase

    @MockK
    lateinit var getAllUseCase: GetAllFavouritesUseCase

    @MockK
    lateinit var isFavUseCase: IsFavouriteUseCase

    private lateinit var viewModel: FavouriteViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcherRule.testDispatcher)

        every { getAllUseCase.invoke() } returns flowOf(emptyList())

        viewModel = FavouriteViewModel(
            addUseCase,
            removeUseCase,
            getAllUseCase,
            isFavUseCase
        )
    }

    @Test
    fun `favourites returns values from use case`() = runTest {
        val movie = FavouriteMovieEntity(1, "Title", "Overview", "Poster")

        every { getAllUseCase.invoke() } returns flowOf(listOf(movie))

        viewModel = FavouriteViewModel(addUseCase, removeUseCase, getAllUseCase, isFavUseCase)

        // Advance to emit stateIn's first value
        advanceUntilIdle()

        viewModel.favourites.test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(movie, result.first())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `removeFromFavourites should call removeFavouriteUseCase`() = runTest {
        coEvery { removeUseCase(1) } just Runs

        // WHEN
        viewModel.removeFromFavourites(1)

        // ADVANCE coroutine to run launched block
        advanceUntilIdle()

        // THEN
        coVerify { removeUseCase(1) }
    }

    @Test
    fun `isFavourite returns StateFlow with correct value`() = runTest {
        val movieId = 1

        every { isFavUseCase(movieId) } returns flowOf(true)
        every { getAllUseCase() } returns flowOf(emptyList())

        viewModel = FavouriteViewModel(addUseCase, removeUseCase, getAllUseCase, isFavUseCase)

        val resultFlow = viewModel.isFavourite(movieId)

        advanceUntilIdle() // allow coroutines to run

        resultFlow.test {
            val item = awaitItem()
            assertTrue(item) // ✅ This should now pass
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `toggleFavourite adds when not favourite`() = runTest {
        val movie = Movie(1, "Test", "path", "Desc")

        every { isFavUseCase(1) } returns flowOf(false)

        coEvery { addUseCase(any()) } just Runs

        viewModel = FavouriteViewModel(addUseCase, removeUseCase, getAllUseCase, isFavUseCase)

        viewModel.toggleFavourite(movie)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            addUseCase(match {
                it.id == 1 &&
                        it.title == "Test" &&
                        it.overview == "Desc" &&
                        it.posterPath == "path"
            })
        }
    }


    @Test
    fun `toggleFavourite removes when favourite`() = runTest {
        val movie = Movie(1, "Test", "Desc", "path")

        every { isFavUseCase(1) } returns flowOf(true)
        coEvery { removeUseCase(1) } just Runs
        every { getAllUseCase() } returns flowOf(emptyList())

        viewModel = FavouriteViewModel(addUseCase, removeUseCase, getAllUseCase, isFavUseCase)

        viewModel.toggleFavourite(movie)

        advanceUntilIdle()

        coVerify(exactly = 1) { removeUseCase(1) }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
