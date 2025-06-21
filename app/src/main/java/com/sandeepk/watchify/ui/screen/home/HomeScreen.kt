package com.sandeepk.watchify.ui.screen.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    MovieList(
        movies = movies,
        isFavourite = { movie -> viewModel.isFavourite(movie) },
        onFavouriteClick = { movie -> viewModel.toggleFavourite(movie) }
    )
}

@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>,
    isFavourite: (Movie) -> Boolean,
    onFavouriteClick: (Movie) -> Unit
) {
    when (movies.loadState.refresh) {
        is LoadState.Loading -> {
            // Show full-screen centered loading
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
            val error = (movies.loadState.refresh as LoadState.Error).error
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${error.localizedMessage}", color = Color.Red)
            }
        }

        else -> {
            // Load content in LazyColumn only when not loading
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                items(movies.itemCount) { index ->
                    val movie = movies[index]
                    movie?.let {
                        MovieCard(
                            movie = it,
                            isFavourite = isFavourite(it),
                            onFavouriteClick = onFavouriteClick
                        )
                    }
                }

                // Append loading
                if (movies.loadState.append is LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                // Append error
                if (movies.loadState.append is LoadState.Error) {
                    val error = (movies.loadState.append as LoadState.Error).error
                    item {
                        Text(
                            text = "Error: ${error.localizedMessage}",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
















