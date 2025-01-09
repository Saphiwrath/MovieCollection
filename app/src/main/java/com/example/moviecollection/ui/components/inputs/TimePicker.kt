package com.example.moviecollection.ui.components.inputs

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.moviecollection.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialPicker(
    onConfirm: (String) -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column {
        TimePicker(
            state = timePickerState,
        )
        Button(onClick = {
            /* TODO Add ask if you want to set timer*/
            val timeString = "${timePickerState.hour}:${timePickerState.minute}"
            onConfirm(timeString)
        }) {
            Text(stringResource(R.string.confirm_selection_button))

        }
    }
}