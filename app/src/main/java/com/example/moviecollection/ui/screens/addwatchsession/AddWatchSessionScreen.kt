package com.example.moviecollection.ui.screens.addwatchsession

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviecollection.R
import com.example.moviecollection.data.models.ListItemData
import com.example.moviecollection.ui.components.inputs.AutoCompleteTextField
import com.example.moviecollection.ui.components.ConfirmFloatingActionButton
import com.example.moviecollection.ui.components.inputs.DatePickerDocked
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.inputs.DialPicker
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.MovieState

@Composable
fun AddWatchSessionScreen(
    navController: NavHostController,
    actions: AddWatchSessionActions,
    state: AddWatchSessionState,
    onSubmit: () -> Boolean,
    movieState: MovieState
) {
    val backStackEntry = navController.previousBackStackEntry
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.add_watch_session_title),
                actions = {

                },
                navigateUp = {navController.navigateUp()}
            )
        },
        floatingActionButton = {
            ConfirmFloatingActionButton{
                val res  = onSubmit()
                if (res) {
                    navController.navigate(backStackEntry?.destination?.route
                        ?: NavigationRoute.Account.route)
                }
            }
        },
    ){ paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(
                    text = stringResource(R.string.movie_title_label),
                    color = MaterialTheme.colorScheme.onSurface
                )
                AutoCompleteTextField(
                    contentDescription = stringResource(R.string.drop_down_movies),
                    selectedAction = actions::setMovie,
                    multiSelect = false,
                    items = movieState.movies.map { ListItemData(
                        id = it.id,
                        name = it.title
                    ) }
                )
            }
            OutlinedTextField(
                value = state.place,
                onValueChange = actions::setPlace,
                label = { Text(stringResource(R.string.screening_place_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            DatePickerDocked(
                actions::setDate
            )
            DialPicker(
                onConfirm = actions::setTime,
            )
            OutlinedTextField(
                value = state.notes,
                onValueChange = actions::setNotes,
                label = { Text(stringResource(R.string.screening_notes_label)) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 20
            )
        }
    }
}