package com.giannism13.movieflix.homeScreen.composables

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"

@Composable
fun MovieListItem(title: String, backDropPath: String?, voteAverage: Double, releaseDate: String) {
	var isFavorite by remember { mutableStateOf(false) }
	Card {
		AsyncImage(
			IMAGE_BASE_URL + backDropPath,
			title,
			contentScale = ContentScale.FillWidth,
			modifier = Modifier.fillMaxWidth().fillMaxHeight(2 / 3f)
		)
		Row(modifier = Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
			Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
			Text(voteAverage.toString(), fontSize = 14.sp)
			Spacer(modifier = Modifier.weight(0.1f))
			Text(releaseDate, modifier = Modifier.weight(1f))
		}
	}
}

@Composable
@Preview
private fun MovieListItemPreview() {
	MovieListItem("Movie Title", "/backdrop.jpg", 7.5, "2021-10-10")
}