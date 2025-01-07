package com.example.moviecollection.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.FilterButton
import com.example.moviecollection.ui.components.StandardAppBar

@Composable
fun AccountScreen(
    navController: NavHostController
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.account_screen_title),
                actions = {
                    FilterButton(/*TODO MAYBE filter watch sessions?*/)
                },
                navigateUp = {navController.navigateUp()}
            )
        }
    ) {paddingValues->
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize(),
        ){
            AccountCard()
            WatchSessionLazyList()
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


@Composable
fun WatchSessionLazyList() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(20) {
            WatchSessionCard(title = "title", date = "12 gennaio")
        }
    }
}

@Composable
fun WatchSessionCard(
    title: String,
    date: String,
) {
    Card (
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