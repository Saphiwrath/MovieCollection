package com.example.moviecollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviecollection.ui.screens.AccountScreen
import com.example.moviecollection.ui.screens.AchievementsScreen
import com.example.moviecollection.ui.screens.addmovie.AddMovieScreen
import com.example.moviecollection.ui.screens.addwatchsession.AddWatchSessionScreen
import com.example.moviecollection.ui.screens.HomeScreen
import com.example.moviecollection.ui.screens.LoginScreen
import com.example.moviecollection.ui.screens.MovieDetailsScreen
import com.example.moviecollection.ui.screens.MovieWatchSessionScreen
import com.example.moviecollection.ui.screens.settings.SettingsScreen
import com.example.moviecollection.ui.screens.SignupScreen
import com.example.moviecollection.ui.screens.WatchSessionDetailsScreen
import com.example.moviecollection.ui.screens.addmovie.AddMovieViewModel
import com.example.moviecollection.ui.screens.addwatchsession.AddWatchSessionViewModel
import com.example.moviecollection.ui.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Login.route,
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
            val addMovieViewModel = koinViewModel<AddMovieViewModel>()
            val state by addMovieViewModel.state.collectAsStateWithLifecycle()
            AddMovieScreen(
                navController,
                actions = addMovieViewModel.actions,
                state = state
            )
        }

        composable(NavigationRoute.AddWatchSession.route) {
            val addWatchSessionViewModel = koinViewModel<AddWatchSessionViewModel>()
            val state by addWatchSessionViewModel.state.collectAsStateWithLifecycle()
            AddWatchSessionScreen(
                navController,
                actions = addWatchSessionViewModel.actions,
                state = state
            )
        }

        composable(NavigationRoute.Login.route) {
            LoginScreen(navController)
        }

        composable(NavigationRoute.Signup.route) {
            SignupScreen(navController)
        }
        composable(NavigationRoute.Achievements.route) {
            AchievementsScreen(navController)
        }
    }
}