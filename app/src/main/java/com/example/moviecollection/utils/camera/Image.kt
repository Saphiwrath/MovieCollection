package com.example.moviecollection.utils.camera

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import java.io.FileNotFoundException

fun uriToBitmap(imageUri: Uri, contentResolver: ContentResolver): Bitmap? {
    Log.d("URI_DEBUG", "URI: $imageUri")
    return try {
        when {
            Build.VERSION.SDK_INT < 28 -> {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            }

            else -> {
                val source = ImageDecoder.createSource(contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            }
        }
    } catch (_: Exception) {
        null
    }
}

fun saveImageToStorage(
    imageUri: Uri,
    contentResolver: ContentResolver,
    name: String = "IMG_${SystemClock.uptimeMillis()}"
) : Uri {
    val bitmap = uriToBitmap(imageUri, contentResolver)

    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    values.put(MediaStore.Images.Media.DISPLAY_NAME, name)
    values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MovieCollection")

    val savedImageUri =
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    val outputStream = savedImageUri?.let { contentResolver.openOutputStream(it) }
        ?: throw FileNotFoundException();

    if (bitmap != null) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
    }
    return savedImageUri
}
