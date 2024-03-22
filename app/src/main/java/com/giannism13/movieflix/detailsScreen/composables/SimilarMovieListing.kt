package com.giannism13.movieflix.detailsScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.giannism13.movieflix.homeScreen.models.MovieListing

@Composable
fun SimilarMovieListing(movie: MovieListing, onClick: (Int) -> Unit){
	val IMAGE_BASE_URL = remember {"https://image.tmdb.org/t/p/w342"}
	Card(modifier = Modifier.clickable(onClick = { onClick(movie.id) }).padding(2.dp)) {
		Box {
			AsyncImage(model = IMAGE_BASE_URL + movie.posterPath, contentDescription = movie.title)
		}
	}
}