package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
	val id: Int = -1,
	val name: String = ""
)
