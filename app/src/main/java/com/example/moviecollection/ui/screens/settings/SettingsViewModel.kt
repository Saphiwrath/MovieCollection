package com.example.moviecollection.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.models.Theme
import com.example.moviecollection.data.repositories.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsState(
    val theme: Theme,
    var username: String
)

interface SettingsActions {
    fun changeTheme(theme: Theme) : Job
    fun setUsername(string: String)
}

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {
    var state = repository.theme.map { SettingsState(it, "username") }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingsState(Theme.System, "username")
    )

    val actions = object : SettingsActions {
        override fun changeTheme(theme: Theme) = viewModelScope.launch{
            repository.setTheme(theme)
        }

        override fun setUsername(username: String) {
            state.value.username = username
            TODO("Not yet implemented")
        }
    }
}