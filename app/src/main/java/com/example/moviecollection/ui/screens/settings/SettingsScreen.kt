package com.example.moviecollection.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.data.models.Theme
import com.example.moviecollection.ui.components.inputs.RadioButtonRow
import com.example.moviecollection.ui.components.SettingsLabelText
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.inputs.InputFieldWithSideLabel
import com.example.moviecollection.ui.components.showSnackBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavHostController,
    actions: SettingsActions,
    state: SettingsState,
    updateUsername: () -> Unit,
    updatePassword: () -> Unit,
    updateEmail: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState()}
    val usernameMessage = stringResource(R.string.username_updated)
    val passwordMessage = stringResource(R.string.password_updated)
    val emailMessage = stringResource(R.string.email_updated)
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
                    updateUsername()
                    scope.launch {
                        showSnackBar(
                            message = usernameMessage,
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
                    updateEmail()
                    scope.launch {
                        showSnackBar(
                            message = emailMessage,
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
                    updatePassword()
                    scope.launch {
                        showSnackBar(
                            message = passwordMessage,
                            snackBarHostState
                        )
                    }
                }
            )
        }
    }
}