package com.example.moviecollection.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.moviecollection.R

@Composable
fun ProfileImage(
    image: String,
) {
    val painter = rememberAsyncImagePainter(model = image)
    val state by painter.state.collectAsStateWithLifecycle()
    if (state is AsyncImagePainter.State.Error) {
        Image(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier.size(70.dp),
            alignment = Alignment.Center
        )
    } else {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun ScreeningCardImage(
    image: String,
    poster: String
) {
    val imagePainter = rememberAsyncImagePainter(
        model = image,
    )
    val posterPainter = rememberAsyncImagePainter(model = poster)
    val imageState by imagePainter.state.collectAsStateWithLifecycle()
    val posterState by posterPainter.state.collectAsStateWithLifecycle()
    if ( imageState is AsyncImagePainter.State.Error) {
        if (posterState is AsyncImagePainter.State.Error) {
            Image(
                imageVector = Icons.Outlined.Image,
                contentDescription = stringResource(R.string.screening_image),
                modifier = Modifier.width(70.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiaryContainer)
            )
        } else {
            Image(
                painter = posterPainter,
                contentDescription = stringResource(R.string.screening_image),
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(0.dp)),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        }
    } else {
        Image(
            painter = imagePainter,
            contentDescription = stringResource(R.string.screening_image),
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(0.dp)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DetailsImage(
    image: String,
    poster: String = ""
) {
    val imagePainter = rememberAsyncImagePainter(
        model = image,
    )
    val posterPainter = rememberAsyncImagePainter(model = poster)
    val imageState by imagePainter.state.collectAsStateWithLifecycle()
    val posterState by posterPainter.state.collectAsStateWithLifecycle()
    if ( imageState is AsyncImagePainter.State.Error) {
        if (posterState is AsyncImagePainter.State.Error) {
            Image(
                imageVector = Icons.Outlined.Image,
                contentDescription = stringResource(R.string.screening_image),
                modifier = Modifier.width(200.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        } else {
            Image(
                painter = posterPainter,
                contentDescription = stringResource(R.string.screening_image),
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(0.dp)),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        }
    } else {
        Image(
            painter = imagePainter,
            contentDescription = stringResource(R.string.screening_image),
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(0.dp)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MoviePoster(
    poster: String
) {
    val painter = rememberAsyncImagePainter(model = poster)
    val state by painter.state.collectAsStateWithLifecycle()
    if (state is AsyncImagePainter.State.Error) {
        Image(
            Icons.Outlined.Image ,
            contentDescription = stringResource(R.string.movie_details_poster_desc),
            modifier = Modifier.size(70.dp),
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
        )
    } else {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.movie_details_poster_desc),
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(0.dp)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}