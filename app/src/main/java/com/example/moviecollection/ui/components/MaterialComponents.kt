package com.example.moviecollection.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.navigation.NavigationRoute

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
fun WatchSessionLazyList(
    navController: NavHostController
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(20) {
            WatchSessionCard(
                title = "title",
                date = "12 gennaio",
                onClick = {navController.navigate(NavigationRoute.WatchSessionDetails.route)},
            )
        }
    }
}