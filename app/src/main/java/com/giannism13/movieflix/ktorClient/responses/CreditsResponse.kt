package com.giannism13.movieflix.ktorClient.responses

import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
	val cast: List<CastMember>,
)
