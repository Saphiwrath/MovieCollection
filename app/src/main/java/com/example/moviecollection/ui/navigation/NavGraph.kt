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
import com.example.moviecollection.ui.screens.login.LoginScreen
import com.example.moviecollection.ui.screens.MovieDetailsScreen
import com.example.moviecollection.ui.screens.MovieWatchSessionScreen
import com.example.moviecollection.ui.screens.settings.SettingsScreen
import com.example.moviecollection.ui.screens.signup.SignupScreen
import com.example.moviecollection.ui.screens.WatchSessionDetailsScreen
import com.example.moviecollection.ui.screens.addmovie.AddMovieViewModel
import com.example.moviecollection.ui.screens.addwatchsession.AddWatchSessionViewModel
import com.example.moviecollection.ui.screens.dbviewmodels.CastViewModel
import com.example.moviecollection.ui.screens.dbviewmodels.GenreViewModel
import com.example.moviecollection.ui.screens.dbviewmodels.MovieViewModel
import com.example.moviecollection.ui.screens.dbviewmodels.UserViewModel
import com.example.moviecollection.ui.screens.login.LoginViewModel
import com.example.moviecollection.ui.screens.settings.SettingsViewModel
import com.example.moviecollection.ui.screens.signup.SignupViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val userViewModel = koinViewModel<UserViewModel>()
    val movieViewModel = koinViewModel<MovieViewModel>()
    val genreViewModel = koinViewModel<GenreViewModel>()
    val castViewModel = koinViewModel<CastViewModel>()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Login.route,
        modifier = modifier
    ) {

        composable(NavigationRoute.Home.route) {
            val userState by userViewModel.state.collectAsStateWithLifecycle()
            HomeScreen(
                navController,
                userState = userState
            )
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
                state = settingsState,
                updateUsername = { userViewModel.actions.changeUsername(settingsState.username) },
                updateEmail = {userViewModel.actions.changeEmail(settingsState.email)},
                updatePassword = {userViewModel.actions.changePassword(settingsState.password)},
                addGenre = { genreViewModel.actions.addGenre(settingsState.toGenre()) }
            )
        }

        composable(NavigationRoute.MovieWatchSessions.route) {
            MovieWatchSessionScreen(navController)
        }

        composable(NavigationRoute.AddMovie.route) {
            val addMovieViewModel = koinViewModel<AddMovieViewModel>()
            val state by addMovieViewModel.state.collectAsStateWithLifecycle()
            val castState by castViewModel.state.collectAsStateWithLifecycle()
            AddMovieScreen(
                navController,
                actions = addMovieViewModel.actions,
                state = state,
                addMovieAction = {
                    if (state.canSubmit) {
                        movieViewModel.actions.addMovie(state.toMovie())
                        navController.navigate(NavigationRoute.Home.route)
                    }
                },
                castState = castState
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
            val loginViewModel = koinViewModel<LoginViewModel>()
            val state by loginViewModel.state.collectAsStateWithLifecycle()
            LoginScreen(
                navController,
                actions = loginViewModel.actions,
                state = state,
                onLogin = userViewModel.actions::attemptLogin
            )
        }

        composable(NavigationRoute.Signup.route) {
            val signupViewModel = koinViewModel<SignupViewModel>()
            val state by signupViewModel.state.collectAsStateWithLifecycle()
            SignupScreen(
                navController,
                actions = signupViewModel.actions,
                state = state,
                onSignup = userViewModel.actions::registerUser
            )
        }

        composable(NavigationRoute.Achievements.route) {
            AchievementsScreen(navController)
        }
    }
}