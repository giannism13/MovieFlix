package com.giannism13.movieflix.homeScreen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
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
	var favoriteMoviesIds: Set<String> = emptySet()

	init {
		viewModelScope.launch(Dispatchers.IO) {
			PreferencesManager.getFavoriteMoviesIds(appContext).collect { ids ->
				favoriteMoviesIds = ids
			}
			Log.v("Datastore", "favorite movies: $favoriteMoviesIds")
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
				movieList.addAll(response.results.map {
					it.copy(isFavorite = favoriteMoviesIds.contains(it.id.toString()))
				})
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

	fun toggleFavorite(movie: MovieListing) {
		viewModelScope.launch(Dispatchers.IO) {
			movie.setFavorite(movie.isFavorite.not(), appContext)
			favoriteMoviesIds = if (movie.isFavorite)
				favoriteMoviesIds.minus(movie.id.toString())
			else
				favoriteMoviesIds.plus(movie.id.toString())
		}
	}

	fun refreshPopularMovies(stopRefresh:() -> Unit) {
		movieList.clear()
		getPopularMoviesPage(1, stopRefresh)
	}
}