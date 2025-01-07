package com.example.moviecollection.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SettingsLabelText(
    text: String,
    labelWidth: Dp = 100.dp
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = androidx.compose.ui.Modifier.width(labelWidth)
    )
}

@Composable
fun StandardTextField(
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = "",
        onValueChange = onValueChange
    )
}