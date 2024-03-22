package com.giannism13.movieflix.detailsScreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.giannism13.movieflix.detailsScreen.composables.ReviewItem
import com.giannism13.movieflix.detailsScreen.composables.SimilarMovieListing

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w1280"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(movieId: Int, viewModel: MovieDetailsViewModel = viewModel(), onSimilarMovieClick:(Int) -> Unit, onBackPressed:() -> Unit) {
	LaunchedEffect(Unit) {
		viewModel.getCompleteMovieDetails(movieId)
	}

	var isFavorite by remember { mutableStateOf(false) } //TODO: Implement favorite movies

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(viewModel.movieDetails.title) },
				navigationIcon = {
					IconButton(onClick = { onBackPressed() }) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
					}
				}
			)
		}
	) { paddingValues ->
		Column(modifier = Modifier
			.padding(paddingValues)
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
		) {
			Box {
				AsyncImage(
					model = IMAGE_BASE_URL + viewModel.movieDetails.image,
					contentDescription = viewModel.movieDetails.title
				)

				if (viewModel.movieDetails.homepage.isNotEmpty())
					FloatingActionButton(onClick = { /*TODO: share movie's homepage*/ }, modifier = Modifier
						.align(Alignment.BottomEnd)
						.padding(10.dp)) {
						Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
					}
			}

			Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
				Icon(
					imageVector = Icons.Filled.Star,
					contentDescription = "Vote Average",
					tint = Color.Yellow
				)
				Text(viewModel.movieDetails.voteAverage.toString())
			}

			Row(modifier = Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
				Text(text = viewModel.movieDetails.releaseDate)
				Spacer(modifier = Modifier.weight(1f))
				IconToggleButton(checked = isFavorite, onCheckedChange = {isFavorite = it}) {
					if(isFavorite)
						Icon(imageVector = Icons.Filled.Favorite, contentDescription ="Favorite", tint = Color.Red)
					else
						Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription ="Favorite", tint = Color.Red)
				}
			}

			Text(
				viewModel.movieDetails.genres.joinToString { it.name },
				modifier = Modifier.padding(5.dp),
				fontWeight = FontWeight.Light
			)

			Spacer(modifier = Modifier.padding(10.dp))
			Text(text = "Description", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 5.dp))
			Text(text = viewModel.movieDetails.overview, overflow = TextOverflow.Visible, textAlign = TextAlign.Start, modifier = Modifier.padding(horizontal = 5.dp))

			Spacer(modifier = Modifier.padding(10.dp))
			Text("Cast", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 5.dp))
			Text(text = viewModel.castList.joinToString { it.name }, overflow = TextOverflow.Visible, textAlign = TextAlign.Start, modifier = Modifier.padding(horizontal = 5.dp))

			Spacer(modifier = Modifier.padding(10.dp))
			Text("Reviews", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 5.dp))

			viewModel.reviewsList.forEach {
				ReviewItem(it)
			}

			Spacer(modifier = Modifier.padding(10.dp))
			Text("Similar Movies", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 5.dp))
			Row(modifier = Modifier.padding(5.dp).horizontalScroll(rememberScrollState())) {
				viewModel.similarMoviesList.forEach {
					SimilarMovieListing(movie = it) {movieId ->
						onSimilarMovieClick(movieId)
					}
				}
			}
		}
	}
}