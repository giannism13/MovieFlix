package com.giannism13.movieflix.homeScreen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.giannism13.movieflix.PreferencesManager
import com.giannism13.movieflix.homeScreen.models.MovieListing
import com.giannism13.movieflix.ktorClient.KtorClient
import com.giannism13.movieflix.ktorClient.responses.PopularMoviesResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
	val movieList = mutableStateListOf<MovieListing>()
	private val appContext = getApplication<Application>().applicationContext
	var favoriteMovieIds by mutableStateOf(setOf<String>())

	init {
		viewModelScope.launch(Dispatchers.IO) {
			PreferencesManager.getFavoriteMovieIds(appContext).collect { ids ->
				favoriteMovieIds = ids
			}
			Log.v("Datastore", "favorite movies: $favoriteMovieIds")
		}
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
				movieList.addAll(response.results)
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

	fun toggleFavorite(movieId: Int) {
		favoriteMovieIds = if (favoriteMovieIds.contains(movieId.toString()))
			favoriteMovieIds.minus(movieId.toString())
		else
			favoriteMovieIds.plus(movieId.toString())
		viewModelScope.launch(Dispatchers.IO) {
			PreferencesManager.saveFavoriteMovieIds(favoriteMovieIds, appContext)
		}
	}

	fun refreshPopularMovies(stopRefresh:() -> Unit) {
		movieList.clear()
		getPopularMoviesPage(1, stopRefresh)
	}
}