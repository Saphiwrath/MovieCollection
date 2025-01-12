package com.example.moviecollection.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.models.Theme
import com.example.moviecollection.data.repositories.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SettingsState(
    val theme: Theme,
    var username: String = "",
    var email: String = "",
    var password: String = ""
)

interface SettingsActions {
    fun changeTheme(theme: Theme) : Job
    fun setUsername(username: String)

    fun setEmail(email: String)

    fun setPassword(password: String)
}

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState(Theme.System))
    val state = _state.asStateFlow()

    val actions = object : SettingsActions {
        override fun changeTheme(theme: Theme) = viewModelScope.launch{
            repository.setTheme(theme)
        }

        override fun setUsername(username: String) {
            _state.update { it.copy(username = username) }
        }

        override fun setEmail(email: String) {
            _state.update { it.copy(email = email) }
        }

        override fun setPassword(password: String) {
            _state.update { it.copy(password = password) }
        }
    }

    init {
        viewModelScope.launch {
            repository.theme.collect { newTheme ->
                _state.update { it.copy(theme = newTheme) }
            }
        }
    }
}