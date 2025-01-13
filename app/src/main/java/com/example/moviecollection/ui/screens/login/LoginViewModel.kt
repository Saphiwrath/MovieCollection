package com.example.moviecollection.ui.screens.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginState(
    val username: String = "",
    val password: String = ""
) {
    val canSubmit get() = username.isNotBlank() && password.isNotBlank()
}

interface LoginActions {
    fun setUsername(username: String)
    fun setPassword(password: String)
}

class LoginViewModel() : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    val actions = object : LoginActions {
        override fun setUsername(username: String) {
            _state.update { it.copy(username = username) }
        }

        override fun setPassword(password: String) {
            _state.update { it.copy(password = password) }
        }
    }
}