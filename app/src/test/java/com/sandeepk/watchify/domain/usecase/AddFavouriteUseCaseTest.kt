package com.sandeepk.watchify.domain.usecase

import com.sandeepk.watchify.data.local.entity.FavouriteMovieEntity
import com.sandeepk.watchify.domain.repository.FavouriteMovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddFavouriteUseCaseTest {

 private val repository: FavouriteMovieRepository = mockk(relaxed = true)
 private lateinit var useCase: AddFavouriteUseCase

 @Before
 fun setup() {
  useCase = AddFavouriteUseCase(repository)
 }

 @Test
 fun `invoke calls repository addFavourite`() = runTest {
  // Arrange
  val movie = FavouriteMovieEntity(1, "Title", "Overview", "Poster")

  // Act
  useCase(movie)

  // Assert
  coVerify { repository.addFavourite(movie) }
 }
}
