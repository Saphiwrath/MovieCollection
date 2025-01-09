package com.example.moviecollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviecollection.data.models.Theme
import com.example.moviecollection.ui.components.CustomNavBar
import com.example.moviecollection.ui.navigation.NavGraph
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.theme.MovieCollectionTheme
import com.example.moviecollection.ui.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

val NO_BOTTOM_BAR_SCREENS = listOf(NavigationRoute.Login.route,
                                    NavigationRoute.Signup.route)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel = koinViewModel<SettingsViewModel>()
            val settingsState by settingsViewModel.state.collectAsStateWithLifecycle()
            MovieCollectionTheme (
                darkTheme = when (settingsState.theme) {
                    Theme.Light -> false
                    Theme.Dark -> true
                    Theme.System -> isSystemInDarkTheme()
                }
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold (
                        bottomBar = {
                            val backStackEntry by navController.currentBackStackEntryAsState()
                            if (!NO_BOTTOM_BAR_SCREENS.contains(backStackEntry?.destination?.route )) {
                                CustomNavBar(navController = navController)
                            }
                        }
                    ){paddingValues ->
                        NavGraph(
                            navController = navController,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}

