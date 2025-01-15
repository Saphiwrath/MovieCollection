package com.example.moviecollection.ui.components.alerts

import android.widget.Toast
import androidx.compose.material.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope

suspend fun showSnackBar(
    message: String,
    snackBarHostState: SnackbarHostState,
) {
    snackBarHostState.showSnackbar(
        message = message,
        duration = SnackbarDuration.Short,
    )
}