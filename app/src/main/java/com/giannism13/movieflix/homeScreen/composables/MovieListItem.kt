package com.giannism13.movieflix.homeScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.giannism13.movieflix.homeScreen.models.MovieListing

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"

@Composable
fun MovieListItem(movie: MovieListing, onClick: (Int) -> Unit) {
	var isFavorite by remember { mutableStateOf(false) } //TODO: Implement favorite movies
	Card(modifier = Modifier.clickable{ onClick(movie.id) }) {
		AsyncImage(
			IMAGE_BASE_URL + movie.backdropPath,
			movie.title,
			contentScale = ContentScale.FillWidth,
			modifier = Modifier.fillMaxWidth().fillMaxHeight(2 / 3f)
		)
		Row(modifier = Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
			Text(movie.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
			Spacer(modifier = Modifier.weight(1f))
			IconToggleButton(checked = isFavorite, onCheckedChange = {isFavorite = it}) {
				if(isFavorite)
					Icon(imageVector = Icons.Filled.Favorite, contentDescription ="Favorite", tint = Color.Red)
				else
					Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription ="Favorite", tint = Color.Red)
			}
		}
		Row(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
			Icon(imageVector = Icons.Filled.Star, contentDescription = "Vote Average", tint = Color.Yellow)
			Text(movie.voteAverage.toString(), fontSize = 14.sp)
			Spacer(modifier = Modifier.weight(0.1f))
			Text(movie.releaseDate, modifier = Modifier.weight(1f))
		}
	}
}