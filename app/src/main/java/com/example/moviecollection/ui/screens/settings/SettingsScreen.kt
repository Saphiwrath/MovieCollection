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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.moviecollection.ui.components.inputs.StandardTextField

@Composable
fun SettingsScreen(
    navController: NavHostController,
    actions: SettingsActions,
    state: SettingsState
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.settings_screen_title),
                actions = {

                },
                navigateUp = {navController.navigateUp()}
            )
        },
    ){paddingValues ->
        val rowSpacedBy = 20.dp
        val scrollState = rememberScrollState()
        var usernameField by remember {
            mutableStateOf("")
        }
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
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(rowSpacedBy),
                modifier = Modifier.fillMaxSize()
            ) {
                SettingsLabelText(
                    text = stringResource(R.string.username_label)
                )
                StandardTextField(
                    value = usernameField,
                    onValueChange = {value -> usernameField = value}
                )
                IconButton(onClick = { actions.setUsername(usernameField) }) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = stringResource(R.string.generic_confirm_button)
                    )
                }
            }
        }
    }
}