package com.example.moviecollection.ui.screens.addmovie

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.moviecollection.R
import com.example.moviecollection.data.models.ListItemData
import com.example.moviecollection.ui.components.inputs.AutoCompleteTextField
import com.example.moviecollection.ui.components.inputs.ClickableLazyList
import com.example.moviecollection.ui.components.ConfirmFloatingActionButton
import com.example.moviecollection.ui.components.inputs.RadioButtonRowWithLabel
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.screens.entityviewmodels.CastState
import com.example.moviecollection.ui.screens.entityviewmodels.GenreState
import com.example.moviecollection.utils.camera.saveImageToStorage
import com.example.moviecollection.utils.camera.takePicture
import java.util.Calendar

@Composable
fun AddMovieScreen(
    navController: NavHostController,
    actions: AddMovieActions,
    state: AddMovieState,
    addMovieAction: () -> Unit,
    castState: CastState,
    genreState: GenreState
) {
    val ctx = LocalContext.current
    var selectedImage by remember { mutableStateOf<Uri>(Uri.EMPTY)}

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->
            if (uri != null) {
                saveImageToStorage(imageUri = uri, ctx.contentResolver)
            }
            selectedImage = uri ?: Uri.EMPTY
        }
    )
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.add_movie_screen_title),
                actions = {}
            ) { navController.navigateUp() }
        },
        floatingActionButton = {
            ConfirmFloatingActionButton {
                addMovieAction()
            }
        },
    ){paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (selectedImage != Uri.EMPTY) {
                        actions.addImage(selectedImage)
                        AsyncImage(
                            ImageRequest.Builder(ctx)
                                .data(selectedImage)
                                .crossfade(true)
                                .build(),
                            stringResource(R.string.profile_image)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = stringResource(R.string.profile_image),
                            modifier = Modifier.size(200.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            singlePhotoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        colors = ButtonDefaults.buttonColors(

                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.choose_picture)

                        )
                    }
                }
            }
            OutlinedTextField(
                value = state.title,
                onValueChange = { actions.setTitle(it) },
                label = { Text(stringResource(R.string.movie_title_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.movie_year_label))
                    NumberPicker(
                        value = state.year,
                        onValueChange = {
                            actions.setYear(it)
                        },
                        range = 1870..Calendar.getInstance().get(Calendar.YEAR),
                        dividersColor = Color.DarkGray
                    )
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(text = stringResource(R.string.movie_director_label))
                AutoCompleteTextField(
                    contentDescription = stringResource(R.string.drop_down_directors),
                    selectedAction = actions::setDirector,
                    multiSelect = false,
                    items = castState.directors.map { ListItemData(id = it.id, name = it.name) }
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(text = stringResource(R.string.movie_actors_label))
                AutoCompleteTextField(
                    contentDescription = stringResource(R.string.drop_down_actors),
                    selectedAction = actions::setActors,
                    multiSelect = true,
                    items = castState.actors.map { ListItemData(id = it.id, name = it.name) }
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(text = stringResource(R.string.movie_genre_label))
                ClickableLazyList(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    selectedAction = actions::setGenres,
                    items = genreState.genres.map { it.name }
                )
            }
            RadioButtonRowWithLabel(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.movie_format_label),
                selectedAction = actions::setFormat
            )
            OutlinedTextField(
                value = state.notes,
                onValueChange = {
                    actions.setNotes(it)
                },
                label = { Text(stringResource(R.string.screening_notes_label)) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 20
            )
        }
    }
}