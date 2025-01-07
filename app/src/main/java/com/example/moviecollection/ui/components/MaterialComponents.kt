package com.example.moviecollection.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MovieFilter
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.moviecollection.R

@Composable
fun NavigateUpButton(onClick: () -> Unit) {
   IconButton(
       onClick = onClick
   ) {
       Icon(
           imageVector = Icons.Outlined.ArrowBack,
           contentDescription = stringResource(R.string.navigate_up_button_desc),
       )
   }
}

@Composable
fun FilterButton(onClick: () -> Unit = {}) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Outlined.List,
            contentDescription = stringResource(R.string.navigate_up_button_desc),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardAppBar(
    title: String,
    actions: @Composable() (RowScope.() -> Unit),
    navigateUp: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Medium
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        actions = actions,
        navigationIcon = { NavigateUpButton(navigateUp) }
    )
}

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