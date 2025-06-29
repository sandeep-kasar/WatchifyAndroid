package com.sandeepk.watchify.ui.screen.componants

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sandeepk.watchify.domain.model.Movie

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    MaterialTheme {
        MovieCard(
            movie = Movie(
                id = 1,
                title = "The Dark Knight",
                overview = "Batman raises the stakes in his war on crime...",
                posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
            ),
            isFavourite = false
        )
    }
}