package com.example.moviecollection.ui.screens.entityviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.relationships.InFormat
import com.example.moviecollection.data.database.relationships.OfGenre
import com.example.moviecollection.data.database.relationships.WithActors
import com.example.moviecollection.data.repositories.RelationshipsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class OfGenreState(
    val ofGenre: List<OfGenre>
)

data class WithActorsState(
    val withActors: List<WithActors>
)

data class InFormatState(
    val inFormat: List<InFormat>
)

class RelationshipsViewModel (
    repository: RelationshipsRepository
): ViewModel() {
    val ofGenreState =  repository.ofGenre.map { OfGenreState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = OfGenreState(emptyList())
    )
    val inFormatState =  repository.inFormat.map { InFormatState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = InFormatState(emptyList())
    )
    val withActorsState =  repository.withActors.map { WithActorsState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = WithActorsState(emptyList())
    )
}