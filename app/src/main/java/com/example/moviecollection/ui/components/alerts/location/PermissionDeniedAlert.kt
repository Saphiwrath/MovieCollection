package com.example.moviecollection.ui.components.alerts.location

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.moviecollection.R
import com.example.moviecollection.utils.permissions.PermissionHandler

@Composable
fun LocationPermissionDeniedAlert(
    show: MutableState<Boolean>,
    locationPermission: PermissionHandler
) {
    if (show.value) {
        AlertDialog(
            title = { Text(stringResource(R.string.location_permission_denied_alert_title)) },
            text = { Text(stringResource(R.string.location_permission_denied_alert_text)) },
            confirmButton = {
                TextButton(onClick = {
                    locationPermission.launchPermissionRequest()
                    show.value = false
                }) {
                    Text(stringResource(R.string.location_permission_denied_alert_grant))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    show.value = false
                }) {
                    Text(stringResource(R.string.location_permission_denied_alert_dismiss))
                }
            },
            onDismissRequest = { show.value = false }
        )
    }
}