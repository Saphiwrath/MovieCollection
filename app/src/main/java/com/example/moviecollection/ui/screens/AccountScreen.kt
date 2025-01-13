package com.example.moviecollection.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.data.database.entities.Screening
import com.example.moviecollection.ui.components.AccountCard
import com.example.moviecollection.ui.components.AddWatchSessionFloatingActionButton
import com.example.moviecollection.ui.components.CustomNavBar
import com.example.moviecollection.ui.components.FilterButton
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.WatchSessionCard
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.LoggedUserState
import com.example.moviecollection.ui.screens.entityviewmodels.MovieState
import com.example.moviecollection.ui.screens.entityviewmodels.ScreeningState

@Composable
fun AccountScreen(
    navController: NavHostController,
    userState: LoggedUserState,
    screeningState: ScreeningState,
    movieState: MovieState
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.account_screen_title),
                actions = {
/*                    FilterButton(*//*TODO MAYBE filter watch sessions?*//*)*/
                },
                navigateUp = {navController.navigateUp()}
            )
        },
        floatingActionButton = {
            AddWatchSessionFloatingActionButton{ navController.navigate(NavigationRoute.AddWatchSession.route) }
        }
    ) {paddingValues->
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize(),
        ){
            AccountCard(
                username = userState.username,
                image = userState.image ?: "",
                email = userState.email,
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(
                    screeningState.screenings
                ) {
                    screening ->
                    val movie = movieState.movies.find { movie -> screening.movieId == movie.id }
                    if (movie != null) {
                        WatchSessionCard(
                            title = movie.title,
                            date = screening.date,
                            image = screening.image,
                            onClick = { navController.navigate(NavigationRoute.WatchSessionDetails.route) },
                        )
                    }
                }
            }
        }
    }
}