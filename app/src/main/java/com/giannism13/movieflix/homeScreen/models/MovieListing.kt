package com.giannism13.movieflix.homeScreen.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListing(
	val id: Int,
	val title: String,
	@SerialName("backdrop_path") val backdropPath: String?,
	@SerialName("release_date") val releaseDate: String,
	@SerialName("vote_average") val voteAverage: Double,
	@SerialName("poster_path") val posterPath : String,
)
