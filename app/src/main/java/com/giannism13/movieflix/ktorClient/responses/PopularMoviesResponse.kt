package com.giannism13.movieflix.ktorClient.responses

import com.giannism13.movieflix.homeScreen.models.MovieListing
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponse(
	val page: Int,
	val results: List<MovieListing>
)