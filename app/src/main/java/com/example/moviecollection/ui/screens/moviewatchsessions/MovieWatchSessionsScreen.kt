package com.example.moviecollection.ui.screens.moviewatchsessions

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
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.database.entities.Screening
import com.example.moviecollection.ui.components.AddWatchSessionFloatingActionButton
import com.example.moviecollection.ui.components.CustomNavBar
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.WatchSessionCard
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.MovieState

@Composable
fun MovieWatchSessionScreen(
    navController: NavHostController,
    screenings: List<Screening>,
    movie: Movie
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.movie_watch_sessions_screen_title) + " " + movie.title,
                actions = {},
                navigateUp = {navController.navigateUp()}
            )
        },
        floatingActionButton = {
            AddWatchSessionFloatingActionButton{
                /*TODO make sure when you click from this screen it autocompletes the title field*/
                navController.navigate(NavigationRoute.AddWatchSession.route)
            }
        },
    ){paddingValues ->
        Column (
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
        ){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(
                    screenings
                ) {
                    screening ->
                    WatchSessionCard(
                        title = movie.title,
                        date = screening.date,
                        image = screening.image,
                        poster = movie.poster
                    ) {
                        navController.navigate(
                            NavigationRoute.WatchSessionDetails.buildRoute(screeningId = screening.id)
                        )
                    }
                }
            }
        }
    }
}