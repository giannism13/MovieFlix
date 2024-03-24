package com.giannism13.movieflix

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.datastore by preferencesDataStore("favorite_movies")

object PreferencesManager {
	suspend fun saveFavoriteMovieId(movieId: String, context: Context) {
		Log.v("Datastore", "saving favorite movie id: $movieId")
		context.datastore.edit { preferences ->
			val favoriteMovieIds = preferences[FAVORITE_MOVIE_IDS] ?: java.util.HashSet()
			preferences[FAVORITE_MOVIE_IDS] = favoriteMovieIds.plus(movieId)
		}
	}

	suspend fun removeFavoriteMovieId(movieId: String, context: Context) {
		Log.v("Datastore", "removing favorite movie id: $movieId")
		context.datastore.edit { preferences ->
			val favoriteMovieIds = preferences[FAVORITE_MOVIE_IDS] ?: java.util.HashSet()
			preferences[FAVORITE_MOVIE_IDS] = favoriteMovieIds.minus(movieId)
		}
	}

	fun getFavoriteMoviesIds(context: Context): Flow<Set<String>> {
		return  context.datastore.data.map { preferences ->
			preferences[FAVORITE_MOVIE_IDS] ?: HashSet()
		}
	}

	private val FAVORITE_MOVIE_IDS = stringSetPreferencesKey("favorite_movie_ids")
}