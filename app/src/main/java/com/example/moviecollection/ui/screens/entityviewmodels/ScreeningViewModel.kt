package com.example.moviecollection.ui.screens.entityviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.Screening
import com.example.moviecollection.data.repositories.ScreeningRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ScreeningState(
    val screenings: List<Screening>
)

interface ScreeningActions {
    fun addScreening(screening: Screening): Job
    fun deleteScreening(screening: Screening): Job
}

class ScreeningViewModel(
    private val repository: ScreeningRepository
): ViewModel() {
    private lateinit var _state: StateFlow<ScreeningState>
    val state: StateFlow<ScreeningState>
        get() = _state

    val actions = object : ScreeningActions {
        override fun addScreening(screening: Screening) = viewModelScope.launch{
            repository.addScreening(screening)
        }

        override fun deleteScreening(screening: Screening) = viewModelScope.launch{
            repository.deleteScreening(screening)
        }

    }
}