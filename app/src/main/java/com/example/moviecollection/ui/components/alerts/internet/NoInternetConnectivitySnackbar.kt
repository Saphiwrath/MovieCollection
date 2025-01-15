package com.example.moviecollection.ui.components.alerts.internet

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.moviecollection.utils.openWirelessSettings

suspend fun NoInternetConnectivitySnackbar(
    snackbarHostState: SnackbarHostState,
    ctx: Context,
    message: String,
    actionLabel: String
) {
    val res = snackbarHostState.showSnackbar(
    message = message,
    actionLabel = actionLabel,
    duration = SnackbarDuration.Long
    )
    if (res == SnackbarResult.ActionPerformed ) {
        openWirelessSettings(ctx)
    }
}