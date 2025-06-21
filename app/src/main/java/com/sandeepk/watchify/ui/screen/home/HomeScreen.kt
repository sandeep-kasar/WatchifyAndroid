package com.sandeepk.watchify.ui.screen.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sandeepk.watchify.domain.model.Movie
import com.sandeepk.watchify.ui.screen.componants.MovieCard
import com.sandeepk.watchify.ui.screen.favourites.FavouriteViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    favViewModel: FavouriteViewModel = hiltViewModel()
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    MovieList(
        movies = movies,
        isFavouriteFlowProvider = { movieId -> favViewModel.isFavourite(movieId) },
        onFavouriteClick = { movie -> favViewModel.toggleFavourite(movie) }
    )
}


@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>,
    onFavouriteClick: (Movie) -> Unit,
    isFavouriteFlowProvider: (Int) -> StateFlow<Boolean> // only passes flow
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            movie?.let {
                val isFavourite = isFavouriteFlowProvider(movie.id ?: 0).collectAsState()
                MovieCard(
                    movie = movie,
                    isFavourite = isFavourite.value,
                    onFavouriteClick = onFavouriteClick
                )
            }
        }
    }
}

















