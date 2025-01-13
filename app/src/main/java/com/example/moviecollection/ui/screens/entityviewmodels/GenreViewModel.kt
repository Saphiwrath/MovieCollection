package com.example.moviecollection.ui.screens.entityviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.repositories.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

data class GenreState(
    val genres: List<Genre>
)

interface GenreActions {
    fun addGenre(genre: Genre): Boolean
}

class GenreViewModel(
    private val repository: GenreRepository
): ViewModel() {
    val state = repository.genres.map { GenreState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GenreState(emptyList())
    )

    private suspend fun checkAndAddGenre(genre: Genre): Boolean {
        val duplicateGenre = repository.findDuplicate(genre.name)
        return if (duplicateGenre == null) {
            repository.addGenre(genre)
            true
        } else false
    }

    val actions = object : GenreActions {
        override fun addGenre(genre: Genre): Boolean {
            return runBlocking(Dispatchers.IO) { checkAndAddGenre(genre) }
        }

    }
}