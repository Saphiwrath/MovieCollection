package com.example.moviecollection.ui.screens

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.AddWatchSessionFloatingActionButton
import com.example.moviecollection.ui.StandardAppBar

@Composable
fun MovieDetailsScreen(
    navController: NavHostController
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
            AddWatchSessionFloatingActionButton(
            /*TODO make sure when you click from this screen,
            * the movie on this screen shows up already in the movie title field */
            )
        }
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
            if (true) {
                Image(
                    imageVector = Icons.Outlined.Image, 
                    contentDescription = stringResource(R.string.movie_details_poster_desc),
                    modifier = Modifier.size(200.dp),
                    alignment = Alignment.Center
                )
            }
            val text = "MOVIE TITLE MOVIE TITLE MOVIE TITLE"
            Text(
                text,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 30.sp,
                overflow = TextOverflow.Visible
            )
            MovieDataRow(label = "year", value = "1999")
            MovieDataRow(label = "genre", value = "BANANE    CAROTE    SUPERMAN")
            MovieDataRow(label = "directed by", value = "PAOLETTO PAOLINI")
            MovieDataRow(label = "starring", value = "PINCO PALLINO    LA PIMPA    MICHAEL JACKSON")
        }
    }
}

@Composable
fun MovieDataRow(
    label: String,
    value: String
) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.width(90.dp)
        )
        Text(
            text = value,
        )
    }
}
