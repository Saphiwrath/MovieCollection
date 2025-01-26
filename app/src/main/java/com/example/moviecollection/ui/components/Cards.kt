package com.example.moviecollection.ui.components

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecollection.R
import com.example.moviecollection.data.database.entities.Screening
import com.example.moviecollection.utils.camera.uriToBitmap
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.Bars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    onClick: () -> Unit = {},
    favorite: () -> Unit = {},
    title: String,
    image: String,
    isFavourite: Boolean
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
                MoviePoster(poster = image)
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(90.dp),
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(onClick = favorite) {
                    Icon(
                        imageVector = if (!isFavourite) Icons.Outlined.FavoriteBorder
                            else Icons.Outlined.Favorite,
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
    image: String,
    poster: String,
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
            ScreeningCardImage(image = image, poster = poster)
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
fun AccountCard (
    username: String,
    email: String,
    image: String
){
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
            ProfileImage(image = image)
            Text(
                username,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                email,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun SelectableCard(
    clickable: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    item: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { clickable() },
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
    ) {
        Text(
            text = item,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun GraphCard(
    daysOfWeek: Array<String>,
    screenings: List<Screening>
) {
    val firstGraphLine = Color.Black
    val columnLabels = arrayOf(
        stringResource(R.string.first_graph_column),
        stringResource(R.string.second_graph_column),
        stringResource(R.string.third_graph_column),
        stringResource(R.string.fourth_graph_column),
        stringResource(R.string.fifth_graph_column),
        stringResource(R.string.sixth_graph_column),
        stringResource(R.string.seventh_graph_column)
    )
    Card (
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .padding(10.dp)
            .wrapContentHeight()
    ){
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.height(380.dp)
        ) {
            Text(
                text = stringResource(id = R.string.graph_label),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            ColumnChart(
                modifier = Modifier
                    .height(250.dp)
                    .padding(horizontal = 22.dp),
                data = remember {
                    listOf(
                        Bars(
                            label = columnLabels[0],
                            values = listOf(
                                Bars.Data(
                                    value = screenings.count { daysOfWeek[0] == (it.date) }.toDouble(),
                                    color = SolidColor(firstGraphLine)
                                )
                            ),
                        ),
                        Bars(
                            label = columnLabels[1],
                            values = listOf(
                                Bars.Data(
                                    value = screenings.count { daysOfWeek[1] == (it.date) }.toDouble(),
                                    color = SolidColor(firstGraphLine)),
                            ),
                        ),
                        Bars(
                            label = columnLabels[2],
                            values = listOf(
                                Bars.Data(
                                    value = screenings.count { daysOfWeek[2] == (it.date) }.toDouble(),
                                    color = SolidColor(firstGraphLine)),
                            ),
                        ),
                        Bars(
                            label = columnLabels[3],
                            values = listOf(
                                Bars.Data(
                                    value = screenings.count { daysOfWeek[3] == (it.date) }.toDouble(),
                                    color = SolidColor(firstGraphLine)),
                            ),
                        ),
                        Bars(
                            label = columnLabels[4],
                            values = listOf(
                                Bars.Data(
                                    value = screenings.count {
                                        Log.d("DEBUG", it.date + " vs " +daysOfWeek[4])
                                        daysOfWeek[4] == (it.date)
                                    }.toDouble(),
                                    color = SolidColor(firstGraphLine)),
                            ),
                        ),
                        Bars(
                            label = columnLabels[5],
                            values = listOf(
                                Bars.Data(
                                    value =screenings.count { daysOfWeek[5] == (it.date) }.toDouble(),
                                    color = SolidColor(firstGraphLine)),
                            ),
                        ),
                        Bars(
                            label = columnLabels[6],
                            values = listOf(
                                Bars.Data(
                                    value = screenings.count { daysOfWeek[6] == (it.date) }.toDouble(),
                                    color = SolidColor(firstGraphLine)),
                            ),
                        ),

                    )
                },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
            )
        }
    }
}

@Composable
fun AchievementCard(
    name: String = "Test Achievement",
    condition: String = "Test your achievements for the 34875235th time!",
    achieved: Boolean = true
) {
    Card (
       colors = CardDefaults.cardColors(
           contentColor = if (achieved) {
               MaterialTheme.colorScheme.onPrimary
           } else Color.LightGray,
           containerColor = if(achieved) {
               MaterialTheme.colorScheme.primary
           } else Color.DarkGray
       ),
        modifier = Modifier.size(200.dp)
    ){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Image(
                imageVector = Icons.Outlined.EmojiEvents,
                contentDescription = stringResource(R.string.achievement_icon),
                modifier = Modifier.size(50.dp),
                colorFilter = ColorFilter.tint(
                    if (achieved) {
                        MaterialTheme.colorScheme.onPrimary
                    } else Color.LightGray
                )
            )
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = condition,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddCastCard(
    nameValue: String,
    onNameValueChange: (String) -> Unit,
    isActor: Boolean,
    isDirector: Boolean,
    onIsActorChange: (Boolean) -> Unit,
    onIsDirectorChange: (Boolean) -> Unit,
    onSubmit: () -> Unit
) {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Column (
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start,
        ){
            Text(
                text = stringResource(R.string.addCastMember_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = nameValue,
                onValueChange = onNameValueChange,
                label = {Text(stringResource(R.string.castName_field_label))}
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                SettingsLabelText(text = stringResource(R.string.isActor_checkbox_label))
                Checkbox(
                    checked = isActor,
                    onCheckedChange = onIsActorChange,
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                SettingsLabelText(text = stringResource(R.string.isDirector_checkbox_label))
                Checkbox(
                    checked = isDirector,
                    onCheckedChange = onIsDirectorChange,
                )
            }
            Row (
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                IconButton(onClick = onSubmit) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = stringResource(R.string.confirm_selection_button)
                    )
                }
            }
        }
    }
}