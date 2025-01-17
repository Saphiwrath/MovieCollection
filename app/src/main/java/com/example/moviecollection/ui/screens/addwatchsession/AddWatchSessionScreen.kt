package com.example.moviecollection.ui.screens.addwatchsession

import android.Manifest
import android.content.Intent
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecollection.R
import com.example.moviecollection.data.models.ListItemData
import com.example.moviecollection.data.remote.OSMDataSource
import com.example.moviecollection.data.remote.OSMPlace
import com.example.moviecollection.ui.components.inputs.AutoCompleteTextField
import com.example.moviecollection.ui.components.ConfirmFloatingActionButton
import com.example.moviecollection.ui.components.inputs.DatePickerDocked
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.alerts.camera.CameraPermissionDeniedAlert
import com.example.moviecollection.ui.components.alerts.internet.NoInternetConnectivitySnackbar
import com.example.moviecollection.ui.components.alerts.location.LocationDisabledAlert
import com.example.moviecollection.ui.components.alerts.location.LocationPermissionDeniedAlert
import com.example.moviecollection.ui.components.alerts.location.LocationPermissionPermanentlyDeniedSnackbar
import com.example.moviecollection.ui.components.inputs.DialPicker
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.MovieState
import com.example.moviecollection.utils.LocationService
import com.example.moviecollection.utils.permissions.PermissionStatus
import com.example.moviecollection.utils.StartMonitoringResult
import com.example.moviecollection.utils.camera.rememberCameraLauncher
import com.example.moviecollection.utils.camera.saveImageToStorage
import com.example.moviecollection.utils.camera.takePicture
import com.example.moviecollection.utils.isOnline
import com.example.moviecollection.utils.permissions.rememberPermission
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddWatchSessionScreen(
    navController: NavHostController,
    actions: AddWatchSessionActions,
    state: AddWatchSessionState,
    onSubmit: () -> Boolean,
    movieState: MovieState
) {
    val ctx = LocalContext.current
    val locationService = koinInject<LocationService>()
    val osmDataSource = koinInject<OSMDataSource>()
    val backStackEntry = navController.previousBackStackEntry
    val snackBarHostState = remember { SnackbarHostState() }

    val showLocationDisabledAlert = remember { mutableStateOf (false) }
    val showLocationPermissionDeniedAlert = remember { mutableStateOf (false) }
    val showPermissionPermanentlyDeniedSnackbar = remember { mutableStateOf (false) }

    val showCameraPermissionDeniedAlert = remember { mutableStateOf(false)}

    val noInternetMessage = stringResource(R.string.no_internet_connectivity_snackbar_message)
    val noInternetActionLabel = stringResource(R.string.no_internet_connectivity_snackbar_actionLabel)
    val alarmTitle = stringResource(R.string.alarm_title)
    val calendarEventTitle = stringResource(R.string.calendar_event_title)

    var place by remember { mutableStateOf <OSMPlace?>(null) }

    val cameraLauncher = rememberCameraLauncher {
            imageUri -> saveImageToStorage(imageUri, ctx.applicationContext.contentResolver)
    }
    val cameraToast = stringResource(R.string.camera_permission_permanently_denied_toast)
    val cameraPermission = rememberPermission(Manifest.permission.CAMERA) {
        when (it) {
            PermissionStatus.Granted -> cameraLauncher.captureImage()
            PermissionStatus.Denied -> showCameraPermissionDeniedAlert.value = true
            PermissionStatus.Unknown -> {}
            PermissionStatus.PermanentlyDenied -> Toast.makeText(ctx, cameraToast, Toast.LENGTH_SHORT).show()
        }
    }
    val capturedImageUri = cameraLauncher.capturedImageUri

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

    val coroutineScope = rememberCoroutineScope()
    fun searchPlaces() = coroutineScope.launch {
        if (isOnline(ctx)) {
            requestLocation()
            val coordinates = locationService.coordinates
            if (coordinates != null) {
                val res = osmDataSource.searchByLatAndLon(
                    coordinates.latitude,
                    coordinates.longitude
                )
                place = res
                actions.setPlace(place!!.displayName)
            }
        } else {
            NoInternetConnectivitySnackbar(
                snackBarHostState,
                ctx,
                noInternetMessage,
                noInternetActionLabel
            )
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
    CameraPermissionDeniedAlert(
        show = showCameraPermissionDeniedAlert,
        cameraPermission = cameraPermission
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
                    IconButton(onClick = ::searchPlaces) {
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
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    enabled = state.time.isNotBlank(),
                    onClick = {
                        val time = LocalTime.parse(state.time, DateTimeFormatter.ofPattern("HH:mm"))
                        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                            putExtra(AlarmClock.EXTRA_MESSAGE, alarmTitle)
                            putExtra(AlarmClock.EXTRA_HOUR, time.hour)
                            putExtra(AlarmClock.EXTRA_MINUTES, time.minute)
                        }
                        if (intent.resolveActivity(ctx.packageManager) != null) {
                            ctx.startActivity(intent)
                        }
                    }
                ) {
                    Text(stringResource(R.string.set_alarm_button))
                }
                Button(
                    enabled = state.date.isNotBlank() && state.time.isNotBlank(),
                    onClick = {
                        val time = LocalTime.parse(state.time, DateTimeFormatter.ofPattern("HH:mm"))
                        val date =
                            LocalDate.parse(state.date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        val dateTime = date.atTime(time)
                        val dateMillis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        val intent = Intent(Intent.ACTION_INSERT).apply {
                            data = CalendarContract.Events.CONTENT_URI
                            putExtra(CalendarContract.Events.TITLE, calendarEventTitle)
                            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateMillis)
                            if (state.place.isNotBlank()) {
                                putExtra(CalendarContract.Events.EVENT_LOCATION, state.place)
                            }
                        }
                        if (intent.resolveActivity(ctx.packageManager) != null) {
                            ctx.startActivity(intent)
                        }
                    }
                ) {
                    Text(stringResource(R.string.calendar_event_button))
                }
            }
            OutlinedTextField(
                value = state.notes,
                onValueChange = actions::setNotes,
                label = { Text(stringResource(R.string.screening_notes_label)) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 20
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (capturedImageUri.path?.isNotEmpty() == true) {
                        actions.addImage(capturedImageUri)
                        AsyncImage(
                            ImageRequest.Builder(ctx)
                                .data(capturedImageUri)
                                .crossfade(true)
                                .build(),
                            stringResource(R.string.profile_image)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = stringResource(R.string.profile_image),
                            modifier = Modifier.size(200.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            takePicture(cameraLauncher, cameraPermission)
                        },
                        colors = ButtonDefaults.buttonColors(

                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.take_profile_picture)

                        )
                    }
                }
            }
        }
    }
}