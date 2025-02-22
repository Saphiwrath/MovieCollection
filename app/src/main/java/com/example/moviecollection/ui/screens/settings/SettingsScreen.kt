package com.example.moviecollection.ui.screens.settings

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.moviecollection.data.models.Theme
import com.example.moviecollection.data.models.results.AddGenreResults
import com.example.moviecollection.ui.components.AddCastCard
import com.example.moviecollection.ui.components.inputs.RadioButtonRow
import com.example.moviecollection.ui.components.SettingsLabelText
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.alerts.camera.CameraPermissionDeniedAlert
import com.example.moviecollection.ui.components.inputs.InputFieldWithSideLabel
import com.example.moviecollection.ui.components.alerts.showSnackBar
import com.example.moviecollection.utils.camera.rememberCameraLauncher
import com.example.moviecollection.utils.permissions.rememberPermission
import com.example.moviecollection.utils.camera.saveImageToStorage
import com.example.moviecollection.utils.camera.takePicture
import com.example.moviecollection.utils.permissions.PermissionStatus
import kotlinx.coroutines.launch
import java.time.Duration

@Composable
fun SettingsScreen(
    navController: NavHostController,
    actions: SettingsActions,
    state: SettingsState,
    updateUsername: () -> Boolean,
    updatePassword: () -> Boolean,
    updateEmail: () -> Boolean,
    addGenre: () -> AddGenreResults,
    addCast: () -> Boolean,
    updateImage: () -> Unit
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState()}

    val showCameraPermissionDeniedAlert = remember { mutableStateOf(false) }
    var savedImageUri by remember { mutableStateOf(Uri.EMPTY)}
    val cameraLauncher = rememberCameraLauncher {
        imageUri ->
        savedImageUri = saveImageToStorage(imageUri, ctx.applicationContext.contentResolver)
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

    val usernameMessage = stringResource(R.string.username_updated)
    val passwordMessage = stringResource(R.string.password_updated)
    val emailMessage = stringResource(R.string.email_updated)
    val genreAddedMessage = stringResource(R.string.genre_added)
    val genreNotAddedMessage = stringResource(R.string.genre_not_added)
    val castMessage = stringResource(R.string.cast_member_added)
    val emptyFieldMessage = stringResource(R.string.empty_text_field)


    CameraPermissionDeniedAlert(
        show = showCameraPermissionDeniedAlert,
        cameraPermission = cameraPermission
    )
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.settings_screen_title),
                actions = {

                },
                navigateUp = {navController.navigateUp()}
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ){paddingValues ->
        val rowSpacedBy = 20.dp
        val scrollState = rememberScrollState()
        Column (
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(rowSpacedBy),
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingsLabelText(
                    text = stringResource(R.string.theme_label)
                )
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .selectableGroup()
                ) {
                    Theme.entries.forEach { theme ->
                        RadioButtonRow(
                            selected = (theme == state.theme),
                            onCLick = {actions.changeTheme(theme)},
                            text = when (theme) {
                                Theme.Light -> stringResource(R.string.light_mode_button)
                                Theme.Dark -> stringResource(R.string.dark_mode_button)
                                Theme.System -> stringResource(R.string.system_mode_button)
                            },
                        )
                    }
                }
            }
            InputFieldWithSideLabel(
                onValueChange = actions::setUsername,
                value = state.username,
                rowSpacedBy = rowSpacedBy,
                text = stringResource(R.string.username_label),
                onClick = {
                    scope.launch {
                        showSnackBar(
                            message = if (updateUsername()) usernameMessage
                                else emptyFieldMessage,
                            snackBarHostState
                        )
                    }
                }
            )
            InputFieldWithSideLabel(
                onValueChange = actions::setEmail,
                value = state.email,
                rowSpacedBy = rowSpacedBy,
                text = stringResource(R.string.email_field_label),
                onClick = {
                    scope.launch {
                        showSnackBar(
                            message = if (updateEmail()) emailMessage
                            else emptyFieldMessage,
                            snackBarHostState
                        )
                    }
                }
            )
            InputFieldWithSideLabel(
                onValueChange = actions::setPassword,
                value = state.password,
                rowSpacedBy = rowSpacedBy,
                text = stringResource(R.string.password_field_label),
                onClick = {
                    scope.launch {
                        showSnackBar(
                            message = if (updatePassword()) passwordMessage
                            else emptyFieldMessage,
                            snackBarHostState
                        )
                    }
                }
            )
            InputFieldWithSideLabel(
                onValueChange = actions::setGenre,
                value = state.genre,
                rowSpacedBy = rowSpacedBy,
                text = stringResource(R.string.genre_field_label),
                onClick = {
                    val res = addGenre()
                    scope.launch {
                        showSnackBar(
                            message = when (res) {
                                AddGenreResults.Success -> genreAddedMessage
                                AddGenreResults.ErrorDuplicate -> genreNotAddedMessage
                                AddGenreResults.ErrorEmptyField -> emptyFieldMessage
                            },
                            snackBarHostState
                        )
                    }
                }
            )
            AddCastCard(
                nameValue = state.castName,
                onNameValueChange = actions::setCastName,
                isActor = state.castIsActor,
                isDirector = state.castIsDirector,
                onIsActorChange = actions::setCastIsActor,
                onIsDirectorChange = actions::setCastIsDirector
            ) {
                scope.launch {
                    showSnackBar(
                        message = if(addCast()) castMessage
                            else emptyFieldMessage,
                        snackBarHostState
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    if (savedImageUri.path?.isNotEmpty() == true) {
                        actions.setImage(savedImageUri)
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
                    TextButton(
                        onClick = updateImage,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.generic_confirm_button)
                        )
                    }
                }
            }
        }
    }
}

