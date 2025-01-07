package com.example.moviecollection.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MovieFilter
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.moviecollection.R

@Composable
fun AddWatchSessionFloatingActionButton(
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        onClick = onClick,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Icon(
            imageVector = Icons.Outlined.MovieFilter,
            contentDescription = stringResource(R.string.add_watch_session_button_desc)
        )
    }
}

@Composable
fun AddMovieFloatingActionButton(
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(R.string.add_movie_button_desc)
        )
    }
}