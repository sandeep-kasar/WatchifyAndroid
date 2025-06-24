package com.sandeepk.watchify.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sandeepk.watchify.domain.model.Movie
import com.sandeepk.watchify.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class GetMoviesUseCaseTest {

 private lateinit var repository: MovieRepository
 private lateinit var useCase: GetMoviesUseCase
 private val testDispatcher = StandardTestDispatcher()

 @Before
 fun setup() {
  repository = mockk()
  useCase = GetMoviesUseCase(repository)
 }

 @Test
 fun invokeShouldReturnExpectedPagingData() = runTest(testDispatcher) {
  // Given
  val movieList = listOf(
   Movie(1, "Movie 1", "Overview 1", "poster1.jpg"),
   Movie(2, "Movie 2", "Overview 2", "poster2.jpg")
  )
  val pagingData = PagingData.from(movieList)
  coEvery { repository.getPagedMovies() } returns flowOf(pagingData)

  // When
  val result = useCase()

  // Then
  val differ = AsyncPagingDataDiffer(
   diffCallback = MovieDiffCallback,
   updateCallback = NoopListUpdateCallback(),
   mainDispatcher = testDispatcher,
   workerDispatcher = testDispatcher
  )

  result.collect { pagingData ->
   differ.submitData(pagingData)
  }

  advanceUntilIdle()

  assertEquals(movieList, differ.snapshot().items)
 }
}

// A basic diff callback for testing
private object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
 override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
 override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}

// No-op callback to satisfy the differ's requirements
private class NoopListUpdateCallback : androidx.recyclerview.widget.ListUpdateCallback {
 override fun onInserted(position: Int, count: Int) {}
 override fun onRemoved(position: Int, count: Int) {}
 override fun onMoved(fromPosition: Int, toPosition: Int) {}
 override fun onChanged(position: Int, count: Int, payload: Any?) {}
}