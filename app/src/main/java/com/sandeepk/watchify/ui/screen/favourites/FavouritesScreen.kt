package com.sandeepk.watchify.ui.screen.favourites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.sandeepk.watchify.ui.screen.componants.MovieCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandeepk.watchify.data.mappers.toMovie

@Composable
fun FavoritesScreen(viewModel: FavouriteViewModel = hiltViewModel()) {
    val favouriteMovies by viewModel.favourites.collectAsState()

    if (favouriteMovies.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No favourite movies yet.", color = Color.Gray)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(favouriteMovies) { favMovie ->
                MovieCard(
                    movie = favMovie.toMovie(),
                    isFavourite = true,
                    onFavouriteClick = {
                        viewModel.removeFromFavourites(favMovie.id)
                    }
                )
            }
        }
    }
}
