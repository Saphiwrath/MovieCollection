package com.example.moviecollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviecollection.ui.screens.AccountScreen
import com.example.moviecollection.ui.screens.AddMovieScreen
import com.example.moviecollection.ui.screens.HomeScreen
import com.example.moviecollection.ui.screens.MovieDetailsScreen
import com.example.moviecollection.ui.screens.MovieWatchSessionScreen
import com.example.moviecollection.ui.screens.SettingsScreen
import com.example.moviecollection.ui.screens.WatchSessionDetailsScreen
import com.example.moviecollection.ui.viewmodels.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Home.route,
        modifier = modifier
    ) {
        composable(NavigationRoute.Home.route) {
            HomeScreen(navController)
        }

        composable(NavigationRoute.MovieDetails.route) {
            MovieDetailsScreen(navController)
        }

        composable(NavigationRoute.Account.route) {
            AccountScreen(navController)
        }

        composable(NavigationRoute.WatchSessionDetails.route) {
            WatchSessionDetailsScreen(navController)
        }

        composable(NavigationRoute.Settings.route) {
            val settingsViewModel = koinViewModel<SettingsViewModel>()
            val settingsState by settingsViewModel.state.collectAsStateWithLifecycle()
            SettingsScreen(
                navController = navController,
                actions = settingsViewModel.actions,
                state = settingsState
            )
        }

        composable(NavigationRoute.MovieWatchSessions.route) {
            MovieWatchSessionScreen(navController)
        }

        composable(NavigationRoute.AddMovie.route) {
            AddMovieScreen(navController)
        }

    }
}