package com.example.moviecollection.ui.screens.account

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.AccountCard
import com.example.moviecollection.ui.components.AddWatchSessionFloatingActionButton
import com.example.moviecollection.ui.components.MapButton
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.WatchSessionCard
import com.example.moviecollection.ui.components.alerts.location.LocationDisabledAlert
import com.example.moviecollection.ui.components.alerts.location.LocationPermissionDeniedAlert
import com.example.moviecollection.ui.components.alerts.location.LocationPermissionPermanentlyDeniedSnackbar
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.LoggedUserState
import com.example.moviecollection.ui.screens.entityviewmodels.MovieState
import com.example.moviecollection.ui.screens.entityviewmodels.ScreeningState
import com.example.moviecollection.utils.LocationService
import com.example.moviecollection.utils.StartMonitoringResult
import com.example.moviecollection.utils.permissions.PermissionStatus
import com.example.moviecollection.utils.permissions.rememberPermission
import org.koin.compose.koinInject

@Composable
fun AccountScreen(
    navController: NavHostController,
    userState: LoggedUserState,
    screeningState: ScreeningState,
    movieState: MovieState
) {
    val ctx = LocalContext.current
    val locationService = koinInject<LocationService>()
    val snackBarHostState = remember { SnackbarHostState() }

    val showLocationDisabledAlert = remember { mutableStateOf (false) }
    val showLocationPermissionDeniedAlert = remember { mutableStateOf (false) }
    val showPermissionPermanentlyDeniedSnackbar = remember { mutableStateOf (false) }

    val locationPermission = rememberPermission(Manifest.permission.ACCESS_COARSE_LOCATION) {
        when (it) {
            PermissionStatus.Unknown -> {}
            PermissionStatus.Granted ->
                showLocationDisabledAlert.value =
                    locationService.requestCurrentLocation() == StartMonitoringResult.GPSDisabled
            PermissionStatus.Denied -> showLocationPermissionDeniedAlert.value = true
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
        show = showLocationPermissionDeniedAlert,
        locationPermission = locationPermission
    )
    LocationPermissionPermanentlyDeniedSnackbar(
        show = showPermissionPermanentlyDeniedSnackbar,
        snackbarHostState = snackBarHostState
    )
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.account_screen_title),
                actions = {
                    MapButton {
                        requestLocation()
                        val coordinates = locationService.coordinates
                        if (coordinates != null) {
                            val geo = Uri.parse(
                                "geo:${coordinates.latitude}, ${coordinates.longitude}?q=Cinema"
                            )
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = geo
                            }
                            if (intent.resolveActivity(ctx.packageManager) != null) {
                                ctx.startActivity(intent)
                            }
                        }
                    }
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
}