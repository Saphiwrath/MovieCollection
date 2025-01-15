package com.example.moviecollection.ui.screens.addwatchsession

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.data.models.ListItemData
import com.example.moviecollection.ui.components.inputs.AutoCompleteTextField
import com.example.moviecollection.ui.components.ConfirmFloatingActionButton
import com.example.moviecollection.ui.components.inputs.DatePickerDocked
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.alerts.location.LocationDisabledAlert
import com.example.moviecollection.ui.components.alerts.location.LocationPermissionDeniedAlert
import com.example.moviecollection.ui.components.alerts.location.LocationPermissionPermanentlyDeniedSnackbar
import com.example.moviecollection.ui.components.inputs.DialPicker
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.MovieState
import com.example.moviecollection.utils.LocationService
import com.example.moviecollection.utils.PermissionStatus
import com.example.moviecollection.utils.StartMonitoringResult
import com.example.moviecollection.utils.rememberPermission
import org.koin.compose.koinInject

@Composable
fun AddWatchSessionScreen(
    navController: NavHostController,
    actions: AddWatchSessionActions,
    state: AddWatchSessionState,
    onSubmit: () -> Boolean,
    movieState: MovieState
) {
    val locationService = koinInject<LocationService>()
    val backStackEntry = navController.previousBackStackEntry
    val snackBarHostState = remember { SnackbarHostState() }

    val showLocationDisabledAlert = remember { mutableStateOf (false) }
    val showPermissionDeniedAlert = remember { mutableStateOf (false) }
    var showPermissionPermanentlyDeniedSnackbar = remember { mutableStateOf (false) }

    val locationPermission = rememberPermission(Manifest.permission.ACCESS_COARSE_LOCATION) {
        when (it) {
            PermissionStatus.Unknown -> {}
            PermissionStatus.Granted ->
                showLocationDisabledAlert.value =
                    locationService.requestCurrentLocation() == StartMonitoringResult.GPSDisabled
            PermissionStatus.Denied -> showPermissionDeniedAlert.value = true
            PermissionStatus.PermanentlyDenied -> showPermissionPermanentlyDeniedSnackbar.value = true
        }
    }

    fun requestLocation() {
        if (locationPermission.status.isGranted) {
            val res = locationService.requestCurrentLocation()
            showLocationDisabledAlert.value = res == StartMonitoringResult.GPSDisabled
        } else {
            locationPermission.launchPermissionRequest()
        }
    }

    LocationDisabledAlert(
        show = showLocationDisabledAlert,
        locationService = locationService
    )
    LocationPermissionDeniedAlert(
        show = showPermissionDeniedAlert,
        locationPermission = locationPermission
    )
    LocationPermissionPermanentlyDeniedSnackbar(
        show = showPermissionPermanentlyDeniedSnackbar,
        snackbarHostState = snackBarHostState
    )
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.add_watch_session_title),
                actions = {},
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
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
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
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        requestLocation()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.MyLocation,
                            contentDescription = stringResource(R.string.my_location)
                        )
                    }
                }
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