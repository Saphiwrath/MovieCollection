package com.example.moviecollection.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.AddMovieFloatingActionButton
import com.example.moviecollection.ui.components.FavouritesButton
import com.example.moviecollection.ui.components.FilterButton
import com.example.moviecollection.ui.components.MovieCard
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.FavouritesState
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
    removeFromFavs: (movieId: Int, userId: Int) -> Unit
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.home_screen_title),
                actions = {
                    FavouritesButton{/*TODO*/ Log.println(Log.DEBUG, TAG, userState.user.username)}
                    FilterButton(/*TODO*/)
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
            items(movieState.movies.toList()) {
                MovieCard (
                    onClick = { navController.navigate(NavigationRoute.MovieDetails.route) },
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