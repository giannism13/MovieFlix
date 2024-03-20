package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.Serializable

@Serializable
data class Review(
	val author: String = "",
	val content: String = ""
)