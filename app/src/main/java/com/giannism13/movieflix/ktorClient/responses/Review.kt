package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
	val author: String = "",
	val content: String = "",
	@SerialName("created_at") val createdAt: String = ""
)