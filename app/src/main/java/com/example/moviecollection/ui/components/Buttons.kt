package com.example.moviecollection.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.moviecollection.R

@Composable
fun FavouritesButton(onClick: () -> Unit = {}) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = stringResource(R.string.favourites_button_desc),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}