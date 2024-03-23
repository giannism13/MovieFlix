package com.giannism13.movieflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.giannism13.movieflix.detailsScreen.MovieDetailsScreen
import com.giannism13.movieflix.homeScreen.HomeScreen
import com.giannism13.movieflix.ui.theme.MovieFlixTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MovieFlixTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val navController = rememberNavController()
					NavHost(navController, startDestination = "home") {
						composable("home") {
							HomeScreen {
								navController.navigate("details/$it")
							}
						}
						composable("details/{movieId}", arguments = listOf(navArgument("movieId") { type = NavType.IntType })) {
							MovieDetailsScreen(it.arguments!!.getInt("movieId"),
								onSimilarMovieClick = {movieId ->
								   navController.navigate("details/$movieId")
								}
							) {
								navController.popBackStack()
							}
						}
					}
				}
			}
		}
	}
}