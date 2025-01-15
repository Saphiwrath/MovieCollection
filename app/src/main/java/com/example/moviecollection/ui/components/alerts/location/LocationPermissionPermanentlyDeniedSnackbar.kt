package com.example.moviecollection.ui.components.alerts.location

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.moviecollection.R

@Composable
fun LocationPermissionPermanentlyDeniedSnackbar(
    show: MutableState<Boolean>,
    snackbarHostState: SnackbarHostState
) {
    val message = stringResource(R.string.location_permission_permanently_denied_snackbar_message)
    val actionLabel = stringResource(R.string.location_permission_permanently_denied_snackbar_actionLabel)
    val ctx = LocalContext.current
    if (show.value) {
        LaunchedEffect(snackbarHostState) {
            val res = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Long
            )
            if (res == SnackbarResult.ActionPerformed ) {
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", ctx.packageName , null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                if (intent.resolveActivity(ctx.packageManager ) != null) {
                    ctx.startActivity(intent)
                }
            }
            show.value = false
        }
    }
}