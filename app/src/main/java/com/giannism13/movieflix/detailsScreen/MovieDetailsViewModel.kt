package com.giannism13.movieflix.detailsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giannism13.movieflix.ktorClient.KtorClient
import com.giannism13.movieflix.ktorClient.responses.CastMember
import com.giannism13.movieflix.ktorClient.responses.CreditsResponse
import com.giannism13.movieflix.ktorClient.responses.MovieDetailsResponse
import com.giannism13.movieflix.ktorClient.responses.Review
import com.giannism13.movieflix.ktorClient.responses.ReviewsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MovieDetailsViewModel: ViewModel() {
	var isLoading by mutableStateOf(false)
		private set
	var movieDetails by mutableStateOf(MovieDetailsResponse())
	var castList by mutableStateOf(listOf<CastMember>())
	private val _reviewsStateList = mutableStateListOf<Review>()
	val reviewsList = _reviewsStateList

	fun getCompleteMovieDetails(movieId: Int) {
		isLoading = true
		viewModelScope.launch(Dispatchers.IO) {
			val deferredJobList = mutableListOf<Deferred<Unit>>()

			deferredJobList.add(async {
				try {
					movieDetails = KtorClient.client.get {
						url {
							it.appendPathSegments(movieId.toString())
						}
					}.body()
				} catch (e: Exception) {
					e.printStackTrace()
				}
			})

			deferredJobList.add(async {
				try {
					castList = KtorClient.client.get {
						url {
							it.appendPathSegments(movieId.toString(), "credits")
						}
					}.body<CreditsResponse>().cast
				} catch (e: Exception) {
					e.printStackTrace()
				}
			})

			deferredJobList.add(async {
				try {
					val result: List<Review> = KtorClient.client.get {
						url {
							it.appendPathSegments(movieId.toString(), "reviews")
						}
					}.body<ReviewsResponse>().results.subList(0, 3)
					val _consumeResult = _reviewsStateList.addAll(result) //need to consume the result to avoid a build error
				} catch (e: Exception) {
					e.printStackTrace()
				}
			})

			//TODO: get similar movies

			deferredJobList.awaitAll()
			isLoading = false
		}
	}
}