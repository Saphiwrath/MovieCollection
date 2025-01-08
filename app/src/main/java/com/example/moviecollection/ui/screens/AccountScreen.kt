package com.example.moviecollection.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.AccountCard
import com.example.moviecollection.ui.components.AddWatchSessionFloatingActionButton
import com.example.moviecollection.ui.components.CustomNavBar
import com.example.moviecollection.ui.components.FilterButton
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.WatchSessionCard
import com.example.moviecollection.ui.components.WatchSessionLazyList
import com.example.moviecollection.ui.navigation.NavigationRoute

@Composable
fun AccountScreen(
    navController: NavHostController
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.account_screen_title),
                actions = {
                    FilterButton(/*TODO MAYBE filter watch sessions?*/)
                },
                navigateUp = {navController.navigateUp()}
            )
        },
        bottomBar = {
            CustomNavBar(navController)
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
            AccountCard()
            WatchSessionLazyList(navController)
        }
    }
}