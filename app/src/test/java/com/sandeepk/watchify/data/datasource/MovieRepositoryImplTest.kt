package com.sandeepk.watchify.data.datasource

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import com.sandeepk.watchify.data.remote.MovieApiService
import com.sandeepk.watchify.domain.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    private lateinit var api: MovieApiService
    private lateinit var repository: MovieRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        repository = MovieRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPagedMovies emits expected data`() = runTest(testDispatcher) {
        // Given
        val mockResponse = MovieResponseDto(
            dates = DatesDto("2025-06-01", "2025-05-01"),
            page = 1,
            results = listOf(
                MovieDto(
                    id = 1,
                    title = "Test Movie 1",
                    overview = "Overview 1",
                    posterPath = "/poster1.jpg"
                ),
                MovieDto(
                    id = 2,
                    title = "Test Movie 2",
                    overview = "Overview 2",
                    posterPath = "/poster2.jpg"
                )
            )
        )

        val expected = listOf(
            Movie(
                id = 1,
                title = "Test Movie 1",
                overview = "Overview 1",
                posterPath = "/poster1.jpg"
            ),
            Movie(
                id = 2,
                title = "Test Movie 2",
                overview = "Overview 2",
                posterPath = "/poster2.jpg"
            )
        )

        coEvery { api.getNowPlaying(any(), any()) } returns mockResponse

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
            repository.getPagedMovies().collectLatest {
                differ.submitData(it)
            }
        }

        // Advance until Paging completes its background operations
        advanceUntilIdle()

        // Then
        assertEquals(expected, differ.snapshot().items)

        // Clean up
        job.cancel()
    }

}

// Required for AsyncPagingDataDiffer
private class NoopListUpdateCallback : androidx.recyclerview.widget.ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}