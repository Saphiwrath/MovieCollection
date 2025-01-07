package com.example.moviecollection.ui.navigation

import androidx.navigation.NamedNavArgument

sealed class NavigationRoute(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Home: NavigationRoute(
        route = "HomeScreen"
    )

    data object MovieDetails: NavigationRoute(
        route = "MovieDetailsScreen"
    )

    data object Account: NavigationRoute(
        route = "AccountScreen"
    )

    data object WatchSessionDetails: NavigationRoute(
        route = "WatchSessionDetailsScreen"
    )

    data object Settings: NavigationRoute(
        route = "SettingsScreen"
    )

    companion object {
        val routes = setOf(Home, NavigationRoute)
    }
}