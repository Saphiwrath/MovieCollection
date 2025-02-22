package com.example.moviecollection.ui.screens.signup

import androidx.lifecycle.ViewModel
import com.example.moviecollection.data.database.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignupState(
    val username: String = "",
    val email: String = "",
    val password: String = ""
){
    val canSubmit get() = username.isNotBlank()
            && email.isNotBlank()
            && password.isNotBlank()

    fun toUser() = User(
        username = username,
        password = password,
        email = email,
        profileImage = null
    )
}

interface SignupActions {
    fun setUsername(username: String)
    fun setEmail(email: String)
    fun setPassword(password: String)
}

class SignupViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state = _state.asStateFlow()

    val actions = object : SignupActions {
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
}