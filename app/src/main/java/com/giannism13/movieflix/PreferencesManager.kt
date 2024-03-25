package com.giannism13.movieflix

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.datastore by preferencesDataStore("favorite_movies")

object PreferencesManager {
	suspend fun saveFavoriteMovieIds(movieIds: Set<String>, context: Context) {
		context.datastore.edit { preferences ->
			preferences[FAVORITE_MOVIE_IDS] = movieIds
		}
	}

	fun getFavoriteMovieIds(context: Context): Flow<Set<String>> {
		return  context.datastore.data.map { preferences ->
			preferences[FAVORITE_MOVIE_IDS] ?: emptySet()
		}
	}

	private val FAVORITE_MOVIE_IDS = stringSetPreferencesKey("favorite_movie_ids")
}