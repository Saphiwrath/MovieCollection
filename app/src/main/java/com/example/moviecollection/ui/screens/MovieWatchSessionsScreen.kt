package com.example.moviecollection.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.AddWatchSessionFloatingActionButton
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.WatchSessionLazyList

@Composable
fun MovieWatchSessionScreen(
    navController: NavHostController
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.movie_watch_sessions_screen_title) + " title",
                actions = {},
                navigateUp = {navController.navigateUp()}
            )
        },
        floatingActionButton = {
            AddWatchSessionFloatingActionButton(
                /*TODO make sure when you click from this screen,
                * the movie on this screen shows up already in the movie title field */
            )
        }
    ){paddingValues ->
        Column (
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
        ){
            WatchSessionLazyList(navController)
        }
    }
}