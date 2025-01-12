package com.example.moviecollection.ui.screens.dbviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.Cast
import com.example.moviecollection.data.repositories.CastRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CastState(
    val directors: List<Cast> = emptyList(),
    val actors: List<Cast> = emptyList()
)

interface CastActions {
    fun addCast(cast: Cast): Job
}

class CastViewModel(
    repository: CastRepository
): ViewModel() {
    private val _state = MutableStateFlow(CastState())
    val state = _state.asStateFlow()

    val actions = object : CastActions {
        override fun addCast(cast: Cast) = viewModelScope.launch{
            repository.addCast(cast)
        }

    }

    init {
        viewModelScope.launch {
            repository.actors.collect { actors ->
                _state.update { it.copy(actors = actors) }
            }
        }
        viewModelScope.launch {
            repository.directors.collect{directors ->
                _state.update { it.copy(directors = directors) }
            }
        }
    }
}