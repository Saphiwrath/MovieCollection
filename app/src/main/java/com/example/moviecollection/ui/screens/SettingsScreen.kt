package com.example.moviecollection.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.data.models.Theme
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.viewmodels.SettingsActions
import com.example.moviecollection.ui.viewmodels.ThemeState

@Composable
fun SettingsScreen(
    navController: NavHostController,
    actions: SettingsActions,
    themeState: ThemeState
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.settings_screen_title),
                actions = {

                },
                navigateUp = {navController.navigateUp()}
            )
        }
    ){paddingValues ->
        val scrollState = rememberScrollState()
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ){
                Text(
                    text = stringResource(R.string.theme_label),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Theme.entries.forEach {theme ->
                    RadioButtonRow(theme = theme, actions = actions, state = themeState)
                }
            }
        }
    }
}

@Composable
fun RadioButtonRow(
    theme: Theme,
    actions: SettingsActions,
    state: ThemeState
) {
    Row (
        modifier = Modifier
            .selectable(
                selected = (theme == state.theme),
                onClick = {actions.changeTheme(theme)},
                role = Role.RadioButton
            ),
    ){
        RadioButton(
            selected = (theme == state.theme),
            onClick = null
        )
        Text(
            text = when (theme) {
                Theme.Light -> stringResource(R.string.light_mode_button)
                Theme.Dark -> stringResource(R.string.dark_mode_button)
                Theme.System -> stringResource(R.string.system_mode_button)
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}