package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
	@SerialName("backdrop_path") val image: String = "",
	val genres: List<Genre> = listOf(Genre()),
	val homepage: String = "",
	val id: Int = -1,
	val overview: String = "",
	val runtime: Int = -1,
	val title: String = "",
	@SerialName("release_date") val releaseDate: String = "",
	@SerialName("vote_average") val voteAverage: Double = -1.0
)