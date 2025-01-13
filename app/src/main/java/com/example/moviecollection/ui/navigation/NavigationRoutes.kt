package com.example.moviecollection.ui.navigation

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationRoute(
    val route: String,
    val title: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Home: NavigationRoute(
        title = "Home",
        route = "HomeScreen"
    )

    data object MovieDetails: NavigationRoute(
        title = "MovieDetails",
        route = "MovieDetailsScreen/{movieId}",
        arguments = listOf(navArgument("movieId") {type = NavType.IntType})
    ) {
        fun buildRoute(movieId: Int) = "MovieDetailsScreen/$movieId"
    }

    data object Account: NavigationRoute(
        title = "Account",
        route = "AccountScreen"
    )

    data object WatchSessionDetails: NavigationRoute(
        title = "WatchSessionDetails",
        route = "WatchSessionDetailsScreen/{screeningId}",
        arguments = listOf(
            navArgument("screeningId"){type = NavType.IntType}
        )
    ) {
        fun buildRoute(screeningId: Int) =
            "WatchSessionDetailsScreen/$screeningId"
    }

    data object Settings: NavigationRoute(
        title = "Settings",
        route = "SettingsScreen"
    )

    data object MovieWatchSessions: NavigationRoute(
        title = "MovieWatchSession",
        route = "MovieWatchSessionsScreen"
    )

    data object AddMovie: NavigationRoute(
        title = "AddMovie",
        route = "AddMovieScreen"
    )
    data object AddWatchSession: NavigationRoute(
        title = "AddWatchSession",
        route = "AddWatchSessionScreen"
    )

    data object Login: NavigationRoute(
        title = "Login",
        route = "LoginScreen"
    )

    data object Signup: NavigationRoute(
        title = "Signup",
        route = "SignupScreen"
    )
    data object Achievements: NavigationRoute(
        title = "Achievements",
        route = "AchievementsScreen"
    )

    companion object {
        val routes = setOf(Home, MovieDetails, Account, WatchSessionDetails, Settings,
                            MovieWatchSessions, AddMovie, AddWatchSession, Login, Signup)
    }
}