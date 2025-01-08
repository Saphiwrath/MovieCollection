package com.example.moviecollection.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.AutoCompleteTextField
import com.example.moviecollection.ui.components.ClickableLazyList
import com.example.moviecollection.ui.components.ConfirmFloatingActionButton
import com.example.moviecollection.ui.components.CustomNavBar
import com.example.moviecollection.ui.components.RadioButtonRowWithLabel
import com.example.moviecollection.ui.components.StandardAppBar

@Composable
fun AddMovieScreen(
    navController: NavHostController
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.add_movie_screen_title),
                actions = {}
            ) { navController.navigateUp() }
        },
        floatingActionButton = {
            ConfirmFloatingActionButton({/*TODO*/})
        },
        bottomBar = {
            CustomNavBar(navController)
        },
    ){paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(stringResource(R.string.movie_title_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(stringResource(R.string.movie_year_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(stringResource(R.string.movie_director_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(text = stringResource(R.string.movie_actors_label))
                AutoCompleteTextField(contentDescription = stringResource(R.string.drop_down_actors))
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(text = stringResource(R.string.movie_genre_label))
                ClickableLazyList(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            }
            RadioButtonRowWithLabel(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.movie_format_label)
            )
        }
    }
}