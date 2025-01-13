package com.example.moviecollection.ui.screens.entityviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.Screening
import com.example.moviecollection.data.repositories.ScreeningRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class ScreeningState(
    val screenings: List<Screening>
)

interface ScreeningActions {
    fun addScreening(screening: Screening): Job
    fun deleteScreening(screening: Screening): Job
    fun getAllScreeningsForUser(userId: Int)
}

class ScreeningViewModel(
    private val repository: ScreeningRepository
): ViewModel() {
    private var _state: MutableStateFlow<ScreeningState> = MutableStateFlow(ScreeningState(emptyList()))
    val state get() = _state.asStateFlow()

    val actions = object : ScreeningActions {
        override fun addScreening(screening: Screening) = viewModelScope.launch{
            repository.addScreening(screening)
        }

        override fun deleteScreening(screening: Screening) = viewModelScope.launch{
            repository.deleteScreening(screening)
        }

        override fun getAllScreeningsForUser(userId: Int) {
            viewModelScope.launch {
                repository.getAllScreeningsForUser(userId).map {
                    ScreeningState(it)
                }.collect {
                    _state.value = it
                }
            }
        }

    }
}