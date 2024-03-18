package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
	val id: Int,
	val title: String,
	@SerialName("backdrop_path") val backdropPath: String?,
	@SerialName("release_date") val releaseDate: String,
	@SerialName("vote_average") val voteAverage: Double,
)
