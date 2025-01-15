package com.example.moviecollection.ui.components.alerts.location

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.moviecollection.R
import com.example.moviecollection.utils.LocationService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDisabledAlert(
    show: MutableState<Boolean>,
    locationService: LocationService
) {
    if (show.value) {
        AlertDialog(
            title = { Text(stringResource(R.string.location_disabled_alert_title)) },
            text = { Text(stringResource(R.string.location_disabled_alert_text)) },
            confirmButton = {
                TextButton(onClick = {
                    show.value = false
                    locationService.openLocationSettings()
                }) {
                    Text(stringResource(R.string.location_disabled_alert_enable))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    show.value = false
                }) {
                    Text(stringResource(R.string.location_disabled_alert_dismiss))
                }
            },
            onDismissRequest = {
                show.value = false
            }
        )
    }
}