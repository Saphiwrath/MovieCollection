package com.example.moviecollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.moviecollection.ui.navigation.NavGraph
import com.example.moviecollection.ui.screens.HomeScreen
import com.example.moviecollection.ui.theme.MovieCollectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCollectionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold (
                        bottomBar = {
                            NavigationBar (
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.wrapContentSize()
                            ) {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier.fillMaxSize()
                                ){
                                    NavBarIconButton(
                                        onClick = { /* TODO */ },
                                        imageVector = Icons.Outlined.EmojiEvents,
                                        contentDescription = stringResource(R.string.achievements_button_desc),
                                    )
                                    NavBarIconButton(
                                        onClick = { /* TODO */ },
                                        imageVector = Icons.Outlined.Settings,
                                        contentDescription = stringResource(R.string.settings_button_desc),
                                    )
                                    NavBarIconButton(
                                        onClick = { /*TODO*/ },
                                        imageVector = Icons.Outlined.AccountCircle,
                                        contentDescription = stringResource(R.string.account_button_desc)
                                    )
                                }
                            }
                        }
                    ){paddingValues ->
                        NavGraph(
                            navController = navController,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavBarIconButton(
    onClick: () -> Unit = {},
    imageVector: ImageVector,
    contentDescription: String,
) {
    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentWidth()
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.wrapContentSize(unbounded = true),
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier.size(100.dp)
            )
        }
        Text(
            text = contentDescription,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.width(100.dp),
            overflow = TextOverflow.Visible,
            textAlign = TextAlign.Center
        )
    }
}