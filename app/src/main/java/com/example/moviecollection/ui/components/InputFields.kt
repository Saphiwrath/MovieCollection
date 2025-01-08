package com.example.moviecollection.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecollection.R
import com.example.moviecollection.data.models.MovieFormat

@Composable
fun StandardTextField(
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange
    )
}

@Composable
fun ClickableLazyList(
    modifier: Modifier,
) {
    val selectedItems = remember { mutableStateListOf<String>()}
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = (1..20).toList().map { "$it" }
        ) {
            SelectableCard(
                clickable = {
                    if (it in selectedItems) {
                        selectedItems.remove(it)
                    } else selectedItems.add(it)
                },
                containerColor =
                    if (it in selectedItems) {
                       MaterialTheme.colorScheme.secondaryContainer
                    } else MaterialTheme.colorScheme.tertiaryContainer,
                contentColor =
                    if (it in selectedItems) {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    } else MaterialTheme.colorScheme.onTertiaryContainer,
                item = it
            )
        }
    }
}

@Composable
fun RadioButtonRowWithLabel(
    modifier: Modifier,
    label: String,
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ){
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            MovieFormat.entries.forEach {
                RadioButtonRow(
                    selected = false,
                    onCLick = { /*TODO*/ },
                    text = when (it) {
                        MovieFormat.DVD -> stringResource(R.string.dvd)
                        MovieFormat.BLURAY -> stringResource(R.string.bluray)
                        MovieFormat.Digital -> stringResource(R.string.digital)
                        MovieFormat.VHS -> stringResource(R.string.vhs)
                    })
            }
        }
    }
}

@Composable
fun RadioButtonRow(
    selected: Boolean,
    onCLick: () -> Unit,
    text: String
) {
    Row (
        modifier = Modifier
            .selectable(
                selected = selected,
                onClick = onCLick,
                role = Role.RadioButton
            ),
    ){
        RadioButton(
            selected = selected,
            onClick = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    contentDescription: String,
    items: List<String> = listOf(
        "arancia", "limone", "fragola", "lampone", "kiwi", "banana",
        "frutta americana"
    ).sorted()
) {
    /*TODO REPLACE*/
    val selectedItems = remember {
        mutableStateListOf<String>()
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    var item by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = item,
                onValueChange = {
                    item = it
                    expanded = true
                },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = contentDescription
                        )
                    }
                }
            )
        }

        AnimatedVisibility( visible = expanded ) {
            Card(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
            ){
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = 150.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        if (item.isNotEmpty()) {
                            items.filter {
                                it.lowercase().contains(item.lowercase())
                            }.sorted()
                        } else items
                    ) {
                        SelectableCard(
                            item = it,
                            clickable = {
                                expanded = false
                                if(it in selectedItems) {
                                    selectedItems.remove(it)
                                } else selectedItems.add(it)
                            },
                            containerColor =
                                if(it in selectedItems) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor =
                                if(it in selectedItems) {
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                } else MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }
    }
}
