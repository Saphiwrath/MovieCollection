package com.example.moviecollection.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.data.models.MovieFormat
import com.example.moviecollection.ui.components.AddMovieFloatingActionButton
import com.example.moviecollection.ui.components.FavouritesButton
import com.example.moviecollection.ui.components.FilterButton
import com.example.moviecollection.ui.components.MovieCard
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.FavouritesState
import com.example.moviecollection.ui.screens.entityviewmodels.InFormatState
import com.example.moviecollection.ui.screens.entityviewmodels.LoggedUserState
import com.example.moviecollection.ui.screens.entityviewmodels.MovieState

const val TAG = "HOME"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    userState: LoggedUserState,
    movieState: MovieState,
    favouritesState: FavouritesState,
    addToFavs: (movieId: Int, userId: Int) -> Unit,
    removeFromFavs: (movieId: Int, userId: Int) -> Unit,
    inFormatState: InFormatState
) {
    val isFavouritesFilterActive = remember { mutableStateOf(false) }
    var isFormatFilterActive by remember { mutableStateOf(false) }

    var selectedFormat by remember {
        mutableStateOf<MovieFormat?>(null)
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.home_screen_title),
                actions = {
                    FavouritesButton{
                        isFavouritesFilterActive.value = !isFavouritesFilterActive.value
                    }
                    Box {
                        FilterButton {
                            expanded = !expanded
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            MovieFormat.entries.forEach {
                                    format ->
                                DropdownMenuItem(
                                    text = {
                                        Text(when (format) {
                                            MovieFormat.DVD -> stringResource(R.string.dvd)
                                            MovieFormat.BLURAY -> stringResource(R.string.bluray)
                                            MovieFormat.Digital -> stringResource(R.string.digital)
                                            MovieFormat.VHS -> stringResource(R.string.vhs)
                                        }
                                        )
                                    },
                                    onClick = {
                                        if (selectedFormat == format) {
                                            isFormatFilterActive = false
                                            selectedFormat = null
                                        } else {
                                            isFormatFilterActive = true
                                            selectedFormat = format
                                        }
                                    },
                                    colors = MenuDefaults.itemColors(
                                        textColor =
                                            if (format == selectedFormat) MaterialTheme.colorScheme.onPrimary
                                            else MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.background(
                                        if (format == selectedFormat) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surface
                                    )
                                )
                            }
                        }
                    }
                },
                navigateUp = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            AddMovieFloatingActionButton {
                navController.navigate(NavigationRoute.AddMovie.route)
            }
        },
    ) {paddingValues ->
        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 80.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(
                if (isFavouritesFilterActive.value) {
                    movieState.movies.filter { it.id in favouritesState.favourites }
                }
                else if (isFormatFilterActive) {
                    movieState.movies.filter {
                        movie ->
                        movie.id in inFormatState.inFormat
                            .filter { it.format == selectedFormat.toString() }
                            .map { it.movieId }
                    }
                }
                else {
                    movieState.movies
                }
            ) {
                MovieCard (
                    onClick = {
                        navController.navigate(
                            NavigationRoute.MovieDetails.buildRoute(movieId = it.id)
                        )
                    },
                    favorite = {
                        if (it.id in favouritesState.favourites) {
                            removeFromFavs(
                                it.id,
                                userState.id
                            )
                        }
                        else addToFavs(
                            it.id,
                            userState.id
                        )
                    },
                    title = it.title,
                    image = it.poster,
                    isFavourite = (it.id in favouritesState.favourites)
                )
            }
        }
    }
}