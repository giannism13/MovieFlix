package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("cast")
data class CastMember(
	val name: String
)
