package com.sandeepk.watchify.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.sandeepk.watchify.domain.model.Movie
import com.sandeepk.watchify.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMoviesUseCaseTest {

    private val repository: MovieRepository = mockk()
    private lateinit var useCase: GetMoviesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = GetMoviesUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke returns expected PagingData`() = runTest {
        // Given
        val movies = listOf(
            Movie(1, "Movie 1", "Overview 1", "/poster1.jpg"),
            Movie(2, "Movie 2", "Overview 2", "/poster2.jpg")
        )
        val pagingData = PagingData.from(movies)

        coEvery { repository.getPagedMovies() } returns flowOf(pagingData)

        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem == newItem
            },
            updateCallback = NoopListUpdateCallback(),
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher
        )

        // When
        val job = launch {
            useCase().collectLatest {
                differ.submitData(it)
            }
        }

        advanceUntilIdle()

        // Then
        assertEquals(movies, differ.snapshot().items)

        job.cancel()
    }
}

private class NoopListUpdateCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
