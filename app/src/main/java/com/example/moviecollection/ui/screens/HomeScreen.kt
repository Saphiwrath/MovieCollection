package com.example.moviecollection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.FilterButton
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.navigation.NavigationRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.home_screen_title),
                actions = {
                    FavouritesButton(/*TODO*/)
                    FilterButton(/*TODO*/)
                },
                navigateUp = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            AddMovieFloatingActionButton(/*TODO*/)
        },
    ) {paddingValues ->
        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 80.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(30) {
                MovieCard (
                    onClick = { navController.navigate(NavigationRoute.MovieDetails.route) },
                    favorite = {/*TODO*/}
                )
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    onClick: () -> Unit = {},
    favorite: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = Modifier
            .size(150.dp)
            .fillMaxWidth(),
        onClick = onClick,
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                /*TODO add actual image if present*/
                if (true) {
                    Image(
                        Icons.Outlined.Image ,
                        contentDescription = "Movie poster",
                        modifier = Modifier.size(70.dp),
                        alignment = Alignment.Center,
                    )
                }
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Movie title",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = favorite) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = stringResource(R.string.favorite_movie_button_desc)
                    )
                }
            }
        }
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