package com.example.moviecollection.ui.screens.dbviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.User
import com.example.moviecollection.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class LoggedUserState(
    val user: User = User()
) {
    val id get() = user.id
    val username get() = user.password
    val email get() = user.email
    val password get() = user.password
    val image get() = user.profileImage
}

interface UserActions{
    suspend fun registerUser(user: User): Boolean
    suspend fun attemptLogin(username: String, password: String): Boolean
    fun changeUsername(username: String) : Job

    fun changeEmail(email: String) : Job

    fun changePassword(password: String) : Job
}

class UserViewModel(
    private val repository: UserRepository
): ViewModel() {
    private lateinit var _state: StateFlow<LoggedUserState>
    val state: StateFlow<LoggedUserState>
        get() = _state

    val actions = object : UserActions {
        override suspend fun registerUser(user: User): Boolean {
            val duplicateEmailAccount = repository.findUserByEmail(user.email)
            return if (duplicateEmailAccount == null) {
                repository.upsert(user)
                true
            } else false
        }

        override suspend fun attemptLogin(username: String, password: String): Boolean {
            val res = repository.attemptLogin(password, username)
            if (res) {
                _state = repository.userFlow.map {
                    LoggedUserState(it)
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = LoggedUserState()
                )
            }
            return res
        }

        override fun changeUsername(username: String) = viewModelScope.launch {
            repository.upsert(
                User (
                    id = _state.value.id,
                    username = username,
                    password = _state.value.password,
                    email = _state.value.email,
                    profileImage = _state.value.image
                )
            )
        }

        override fun changePassword(password: String) = viewModelScope.launch {
            repository.upsert(
                User (
                    id = _state.value.id,
                    username = _state.value.username,
                    password = password,
                    email = _state.value.email,
                    profileImage = _state.value.image
                )
            )
        }

        override fun changeEmail(email: String) = viewModelScope.launch {
            repository.upsert(
                User (
                    id = _state.value.id,
                    username = _state.value.username,
                    password = _state.value.password,
                    email = email,
                    profileImage = _state.value.image
                )
            )
        }
    }
}
