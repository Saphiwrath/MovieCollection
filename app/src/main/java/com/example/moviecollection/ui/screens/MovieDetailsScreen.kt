package com.example.moviecollection.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.database.relationships.InFormat
import com.example.moviecollection.data.database.relationships.OfGenre
import com.example.moviecollection.data.database.relationships.WithActors
import com.example.moviecollection.ui.components.AddWatchSessionFloatingActionButton
import com.example.moviecollection.ui.components.CustomNavBar
import com.example.moviecollection.ui.components.DetailsCardDataRow
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.components.ViewWatchSessionsFloatingActionButton
import com.example.moviecollection.ui.navigation.NavigationRoute
import com.example.moviecollection.ui.screens.entityviewmodels.CastState
import com.example.moviecollection.ui.screens.entityviewmodels.InFormatState
import com.example.moviecollection.ui.screens.entityviewmodels.OfGenreState
import com.example.moviecollection.ui.screens.entityviewmodels.WithActorsState

const val TAB = "    "

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    movie: Movie,
    withActors: WithActorsState,
    inFormat: InFormatState,
    castState: CastState,
    ofGenre: OfGenreState
) {
    Scaffold(
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.movie_details_screen_title),
                actions = {

                },
                navigateUp = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            ViewWatchSessionsFloatingActionButton {
                /* TODO */
                navController.navigate(NavigationRoute.MovieWatchSessions.route)
            }
        },
    ) {paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(12.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            /* TODO: Add actual movie image if present */
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                if (movie.poster.isBlank()) {
                    Icon(
                        imageVector = Icons.Outlined.Image,
                        contentDescription = stringResource(R.string.movie_details_poster_desc),
                        modifier = Modifier.size(200.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Text(
                movie.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 30.sp,
                overflow = TextOverflow.Visible
            )

            DetailsCardDataRow(label = stringResource(R.string.year_label), value = movie.year.toString())

            var genreLabel = ""
            ofGenre.ofGenre
                .filter { it.movieId == movie.id }
                .map { it.genre }
                .forEach {
                    genreLabel += it.uppercase() + TAB
                }
            DetailsCardDataRow(
                label = stringResource(R.string.genre_label),
                value = genreLabel.trim()
            )

            DetailsCardDataRow(
                label = stringResource(R.string.director_label),
                value = castState.directors
                    .find { movie.directorId == it.id }
                    ?.name
                    ?.uppercase() ?: ""
            )

            var actorsLabel = ""
            withActors.withActors
                .filter { movie.id == it.movieId }
                .map { withactor ->
                    requireNotNull(castState.actors
                        .find { it.id == withactor.castId }
                    ).name
                }
                .forEach {
                    actorsLabel += it.uppercase() + TAB
                }
            DetailsCardDataRow(
                label = stringResource(R.string.actors_label),
                value = actorsLabel.trim()
            )

            var formatsLabel = ""
            inFormat.inFormat
                .filter { movie.id == it.movieId }
                .forEach {
                    formatsLabel += it.format.uppercase() + TAB
                }
            DetailsCardDataRow(
                label = stringResource(R.string.format_label),
                value = formatsLabel.trim()
            )

            DetailsCardDataRow(label = stringResource(R.string.notes_label), value = movie.notes)
        }
    }
}
