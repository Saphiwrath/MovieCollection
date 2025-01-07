package com.example.moviecollection.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.StandardAppBar

@Composable
fun WatchSessionDetailsScreen(
    navController: NavHostController
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.watch_session_details_title),
                actions = {

                },
                navigateUp = {navController.navigateUp()}
            )
        }
    ){

    }
}