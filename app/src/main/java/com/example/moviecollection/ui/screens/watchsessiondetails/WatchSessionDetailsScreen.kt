package com.example.moviecollection.ui.screens.watchsessiondetails

import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.database.entities.Screening
import com.example.moviecollection.ui.components.DetailsCardDataRow
import com.example.moviecollection.ui.components.DetailsImage
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.utils.camera.uriToBitmap

@Composable
fun WatchSessionDetailsScreen(
    navController: NavHostController,
    screening: Screening,
    movie: Movie
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.watch_session_details_title),
                actions = {

                },
                navigateUp = {navController.navigateUp()}
            )
        },
    ){paddingValues ->
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
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                DetailsImage(image = screening.image, poster = movie.poster)
            }
            Text(
                movie.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Visible,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            DetailsCardDataRow(label = stringResource(R.string.place_label), value = screening.place)
            DetailsCardDataRow(label = stringResource(R.string.date_label), value = screening.date)
            DetailsCardDataRow(label = stringResource(R.string.time_label), value = screening.time)
            DetailsCardDataRow(label = stringResource(R.string.notes_label), value = screening.notes)
        }

    }
}