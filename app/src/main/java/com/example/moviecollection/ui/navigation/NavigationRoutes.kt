package com.example.moviecollection.ui.navigation

import androidx.navigation.NamedNavArgument

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
        route = "MovieDetailsScreen"
    )

    data object Account: NavigationRoute(
        title = "Account",
        route = "AccountScreen"
    )

    data object WatchSessionDetails: NavigationRoute(
        title = "WatchSessionDetails",
        route = "WatchSessionDetailsScreen"
    )

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

    companion object {
        val routes = setOf(Home, MovieDetails, Account, WatchSessionDetails, Settings,
                            MovieWatchSessions, AddMovie, AddWatchSession, Login, Signup)
    }
}