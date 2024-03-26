package com.giannism13.movieflix.detailsScreen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.giannism13.movieflix.PreferencesManager
import com.giannism13.movieflix.homeScreen.models.MovieListing
import com.giannism13.movieflix.ktorClient.KtorClient
import com.giannism13.movieflix.ktorClient.responses.CastMember
import com.giannism13.movieflix.ktorClient.responses.CreditsResponse
import com.giannism13.movieflix.ktorClient.responses.MovieDetailsResponse
import com.giannism13.movieflix.ktorClient.responses.PopularMoviesResponse
import com.giannism13.movieflix.ktorClient.responses.Review
import com.giannism13.movieflix.ktorClient.responses.ReviewsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MovieDetailsViewModel(application: Application): AndroidViewModel(application) {
	var isLoading by mutableStateOf(false)
		private set
	var movieDetails by mutableStateOf(MovieDetailsResponse())
	var castList by mutableStateOf(listOf<CastMember>())
	val reviewsList = mutableStateListOf<Review>()
	val similarMoviesList = mutableStateListOf<MovieListing>()
	var favoriteMovieIds by mutableStateOf(setOf<String>())

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
						this.parameter("page", 1)
					}.body<ReviewsResponse>().results.subList(0, 3)
					val _consumeResult = reviewsList.addAll(result) //need to consume the result to avoid a build error
				} catch (e: Exception) {
					e.printStackTrace()
				}
			})

			deferredJobList.add(async {
				try {
					val result: List<MovieListing> = KtorClient.client.get {
						url {
							it.appendPathSegments(movieId.toString(), "similar")
						}
						this.parameter("page", 1)
					}.body<PopularMoviesResponse>().results.subList(0, 6)
					val _consumeResult = similarMoviesList.addAll(result) //need to consume the result to avoid a build error
				} catch (e: Exception) {
					e.printStackTrace()
				}
			})

			deferredJobList.add(async {
				PreferencesManager.getFavoriteMovieIds(getApplication<Application>().applicationContext).collect { ids ->
					favoriteMovieIds = ids
				}
			})

			deferredJobList.awaitAll()
			isLoading = false
		}
	}

	fun toggleFavorite(movieId: Int) {
		favoriteMovieIds = if (favoriteMovieIds.contains(movieId.toString()))
			favoriteMovieIds.minus(movieId.toString())
		else
			favoriteMovieIds.plus(movieId.toString())
		viewModelScope.launch(Dispatchers.IO) {
			PreferencesManager.saveFavoriteMovieIds(favoriteMovieIds, getApplication<Application>().applicationContext)
		}
	}
}