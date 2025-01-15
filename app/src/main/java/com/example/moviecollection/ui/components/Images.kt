package com.example.moviecollection.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecollection.R
import com.example.moviecollection.utils.camera.uriToBitmap

@Composable
fun ProfileImage(
    image: Bitmap?,
) {
    Image(
        bitmap = image!!.asImageBitmap(),
        contentDescription = stringResource(R.string.profile_image),
        modifier = Modifier.size(70.dp).clip(CircleShape),
        alignment = Alignment.Center,
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun ScreeningCardImage(
    image: Bitmap?,
    poster: Bitmap?
) {
    Image(
        bitmap = (image ?: poster)
                !!.asImageBitmap(),
        contentDescription = stringResource(R.string.screening_image),
        modifier = Modifier.size(70.dp).clip(RoundedCornerShape(0.dp)),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DetailsImage(
    image: Bitmap?,
    poster: Bitmap?
) {
    Image(
        bitmap = (image ?: poster)!!.asImageBitmap(),
        contentDescription = stringResource(R.string.screening_image),
        modifier = Modifier.size(200.dp).clip(RoundedCornerShape(0.dp)),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
    )
}