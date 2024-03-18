package com.giannism13.movieflix.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giannism13.movieflix.homeScreen.composables.MovieListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
	val listState = rememberLazyListState()
	val coroutineScope = rememberCoroutineScope()
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(onClick = {
				coroutineScope.launch {
					listState.animateScrollToItem(0)
				}
			}){ Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "scroll to top")}
		}
	) { paddingValues ->

		val reachedBottom: Boolean by remember {
			derivedStateOf {
				val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
				val totalItemsCount = listState.layoutInfo.totalItemsCount
				val scrolledPercentage = (lastVisibleItem?.index ?: 0) / totalItemsCount.toDouble()
				scrolledPercentage >= 0.8
			}
		}
		val pullRefreshState = rememberPullToRefreshState()

		LaunchedEffect(reachedBottom) {
			if (reachedBottom)
				viewModel.getPopularMoviesPage(viewModel.movieList.size / 20 + 1)
		}

		LaunchedEffect(pullRefreshState.isRefreshing) {
			if (pullRefreshState.isRefreshing) {
				viewModel.refreshPopularMovies {
					pullRefreshState.endRefresh()
				}
			}
		}

		Box(
			Modifier.nestedScroll(pullRefreshState.nestedScrollConnection),
			contentAlignment = Alignment.TopCenter
		) {
			LazyColumn(
				state = listState,
				modifier = Modifier.consumeWindowInsets(paddingValues),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(10.dp),
				contentPadding = PaddingValues(10.dp)
			) {
				items(viewModel.movieList) {
					MovieListItem(
						title = it.title,
						backDropPath = it.backdropPath,
						voteAverage = it.voteAverage,
						releaseDate = it.releaseDate
					)
				}
			}
			PullToRefreshContainer(state = pullRefreshState)
		}
	}
}