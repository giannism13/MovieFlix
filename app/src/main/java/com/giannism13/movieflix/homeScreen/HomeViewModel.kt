package com.giannism13.movieflix.homeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giannism13.movieflix.homeScreen.models.MovieListing
import com.giannism13.movieflix.ktorClient.KtorClient
import com.giannism13.movieflix.ktorClient.responses.PopularMoviesResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
	private val _movieStateList = mutableStateListOf<MovieListing>()
	val movieList = _movieStateList

	init {
		getPopularMoviesPage(1)
	}

	fun getPopularMoviesPage(page: Int, stopRefresh: (() -> Unit)? = null) {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val response:PopularMoviesResponse = KtorClient.client.get{
					this.parameter("page", page)
					url{
						it.appendPathSegments("popular")
					}
				}.body()
				Log.v("HomeViewModel", "movies in page $page: ${response.results.size}")
				_movieStateList.addAll(response.results)
			}
			catch (e: Exception) {
				e.printStackTrace()
			}
			finally {
				if (stopRefresh != null)
					stopRefresh()
			}
		}
	}

	fun refreshPopularMovies(stopRefresh:() -> Unit) {
		_movieStateList.clear()
		getPopularMoviesPage(1, stopRefresh)
	}
}