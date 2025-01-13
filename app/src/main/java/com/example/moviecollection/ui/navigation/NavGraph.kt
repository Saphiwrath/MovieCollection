package com.example.moviecollection.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.database.entities.Screening
import com.example.moviecollection.data.models.results.LoginResult
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
import com.example.moviecollection.ui.screens.entityviewmodels.CastViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.GenreViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.MovieViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.ScreeningViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.UserViewModel
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
    val screeningViewModel = koinViewModel<ScreeningViewModel>()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Login.route,
        modifier = modifier
    ) {

        fun initializeELementLists() {
            val userId = userViewModel.state.value.id
            movieViewModel.actions.getAllMoviesAndFavouritesForUser(userId)
            screeningViewModel.actions.getAllScreeningsForUser(userId)
        }

        composable(NavigationRoute.Home.route) {
            val userState by userViewModel.state.collectAsStateWithLifecycle()
            // Initialize every element list
            initializeELementLists()
            val movieState by movieViewModel.movieState.collectAsStateWithLifecycle()
            val favouritesState by movieViewModel.favouritesState.collectAsStateWithLifecycle()
            HomeScreen(
                navController,
                userState = userState,
                movieState = movieState,
                favouritesState = favouritesState,
                addToFavs = movieViewModel.actions::addToFavourites,
                removeFromFavs = movieViewModel.actions::removeFromFavourites
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
                addGenre = {
                    if (settingsState.canSubmitGenre) genreViewModel.actions.addGenre(settingsState.toGenre())
                    else false
                },
                addCast = {
                    if (settingsState.canSubmitActor) {
                        castViewModel.actions.addCast(settingsState.toCast())
                        true
                    }
                    else false
                }
            )
        }

        composable(NavigationRoute.MovieWatchSessions.route) {
            MovieWatchSessionScreen(navController)
        }

        composable(NavigationRoute.AddMovie.route) {
            val addMovieViewModel = koinViewModel<AddMovieViewModel>()
            val state by addMovieViewModel.state.collectAsStateWithLifecycle()
            val castState by castViewModel.state.collectAsStateWithLifecycle()
            val genreState by genreViewModel.state.collectAsStateWithLifecycle()
            AddMovieScreen(
                navController,
                actions = addMovieViewModel.actions,
                state = state,
                addMovieAction = {
                    if (state.canSubmit) {
                        movieViewModel.actions.addMovieWithRels(
                            movie = state.toMovie(),
                            genres = state.genres.map { Genre(it) },
                            actors = state.actors,
                            formats = state.format,
                            userId = userViewModel.state.value.id
                        )
                        navController.navigate(NavigationRoute.Home.route)
                    }
                },
                castState = castState,
                genreState = genreState
            )
        }

        composable(NavigationRoute.AddWatchSession.route) {
            val addWatchSessionViewModel = koinViewModel<AddWatchSessionViewModel>()
            val state by addWatchSessionViewModel.state.collectAsStateWithLifecycle()
            val movieState by movieViewModel.movieState.collectAsStateWithLifecycle()
            AddWatchSessionScreen(
                navController,
                actions = addWatchSessionViewModel.actions,
                state = state,
                onSubmit = {
                    if (state.canSubmit) screeningViewModel.actions.addScreening(
                        Screening(
                            movieId = state.movieId,
                            time = state.time,
                            date = state.date,
                            notes = state.notes,
                            image = state.image.toString(),
                            place = state.place,
                            userId = userViewModel.state.value.id
                        )
                    ) else Log.d("DEBUG", "no can do")
                },
                movieState = movieState
            )
        }

        composable(NavigationRoute.Login.route) {
            val loginViewModel = koinViewModel<LoginViewModel>()
            val state by loginViewModel.state.collectAsStateWithLifecycle()
            val userState by userViewModel.state.collectAsStateWithLifecycle()
            LoginScreen(
                navController,
                actions = loginViewModel.actions,
                state = state,
                onLogin = {
                    if (state.canSubmit) {
                        val success = userViewModel.actions.attemptLogin(
                            username = state.username,
                            password = state.password
                        )
                        if (success) {
                            LoginResult.Success
                        } else LoginResult.WrongCredentials
                    } else LoginResult.CannotSubmit
                }
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