package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponse(
	val page: Int,
	val results: List<Movie>
)