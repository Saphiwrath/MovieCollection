package com.example.moviecollection.ui.navigation

import androidx.navigation.NamedNavArgument

sealed class NavigationRoute(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Home: NavigationRoute(
        route = "HomeScreen"
    )

    companion object {
        val routes = setOf(Home)
    }
}