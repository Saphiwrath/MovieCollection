package com.example.moviecollection.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moviecollection.R

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
fun DetailsCardDataRow(
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchSessionCard(
    title: String,
    date: String,
    onClick: () -> Unit = {}
) {
    Card (
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
        modifier = Modifier
            .height(80.dp)
            .padding(5.dp)
            .fillMaxWidth(),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            if(true) {
                Image(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = stringResource(R.string.movie_details_poster_desc),
                    modifier = Modifier.width(70.dp)
                )
            }
            Column (
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            ){
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    date,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}


@Composable
fun AccountCard (){
    Card (
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            /*TODO Add real image if present*/
            if(true) {
                Image(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = stringResource(R.string.profile_image),
                    modifier = Modifier.size(70.dp),
                    alignment = Alignment.Center
                )
            }
            Text(
                "Username",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}