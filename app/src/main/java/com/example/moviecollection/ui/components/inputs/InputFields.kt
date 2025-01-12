package com.example.moviecollection.ui.components.inputs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moviecollection.R
import com.example.moviecollection.data.models.ListItemData
import com.example.moviecollection.data.models.MovieFormat
import com.example.moviecollection.ui.components.SelectableCard
import com.example.moviecollection.ui.components.SettingsLabelText
import com.example.moviecollection.ui.components.showSnackBar
import kotlinx.coroutines.launch

@Composable
fun InputFieldWithSideLabel(
    onClick: () -> Unit,
    onValueChange: (String) -> Unit,
    value: String,
    rowSpacedBy: Dp,
    text: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(rowSpacedBy),
        modifier = Modifier.fillMaxSize()
    ) {
        SettingsLabelText(
            text = text
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            trailingIcon = {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = stringResource(R.string.generic_confirm_button)
                    )
                }
            }
        )
    }
}

@Composable
fun ClickableLazyList(
    modifier: Modifier,
    selectedAction: (List<String>) -> Unit
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
                    selectedAction(selectedItems)
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
    selectedAction: (List<MovieFormat>) -> Unit
) {
    val selectedItems = remember {
        mutableStateListOf<MovieFormat>()
    }
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
                    selected = (selectedItems.contains(it)),
                    onCLick = {
                        if (it in selectedItems) {
                            selectedItems.remove(it)
                        } else selectedItems.add(it)
                        selectedAction(selectedItems)
                    },
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
    items: List<ListItemData> = listOf(
        ListItemData(1, "Paoletto Paolini"),
        ListItemData(2, "La Pimpa"),
        ListItemData(3, "Armando Pimpa"),
        ListItemData(4, "Paolo Pimpa")
    ),
    selectedAction: (List<Int>) -> Unit,
    multiSelect: Boolean = true
) {
    val selectedItems = remember {
        mutableStateListOf<Int>()
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
                                it.name.lowercase().contains(item.lowercase())
                            }
                        } else items
                    ) {
                        SelectableCard(
                            item = it.name,
                            clickable = {
                                expanded = false
                                if (multiSelect) {
                                    if(it.id in selectedItems) {
                                        selectedItems.remove(it.id)
                                    } else selectedItems.add(it.id)
                                }
                                else {
                                    if (selectedItems.isNotEmpty()) {
                                        selectedItems.removeLast()
                                    }
                                    selectedItems.add(it.id)
                                }
                                selectedAction(selectedItems)
                            },
                            containerColor =
                                if(it.id in selectedItems) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor =
                                if(it.id in selectedItems) {
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                } else MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }
    }
}
