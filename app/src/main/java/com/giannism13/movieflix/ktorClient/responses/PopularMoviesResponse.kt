package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponse(
	val page: Int,
	@SerialName("total_pages") val totalPages: Int,
	@SerialName("total_results") val totalResults: Int,
	val results: List<Movie>
)