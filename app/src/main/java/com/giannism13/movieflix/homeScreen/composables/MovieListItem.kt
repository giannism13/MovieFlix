package com.giannism13.movieflix.homeScreen.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"

@Composable
fun MovieListItem(title: String, backDropPath: String?, voteAverage: Double, releaseDate: String) {
	Card () {
		AsyncImage(
			IMAGE_BASE_URL + backDropPath,
			title,
			contentScale = ContentScale.FillWidth,
			modifier = Modifier.fillMaxWidth().fillMaxHeight(2/3f)
		)
		Text(title)
		Text(voteAverage.toString())
		Text(releaseDate)
	}
}