package com.example.moviecollection.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.models.Theme
import com.example.moviecollection.data.repositories.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ThemeState(
    val theme: Theme
)

interface SettingsActions {
    fun changeTheme(theme: Theme) : Job
}

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {
    val themeState = repository.theme.map { ThemeState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ThemeState(Theme.System)
    )

    val actions = object : SettingsActions {
        override fun changeTheme(theme: Theme) = viewModelScope.launch{
            repository.setTheme(theme)
        }
    }
}