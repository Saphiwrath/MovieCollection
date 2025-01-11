package com.example.moviecollection.ui.screens.dbviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.User
import com.example.moviecollection.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class LoggedUserState(
    val email: String = "",
    val username: String = "",
    val image: String? = null,
    val id: Int = -1
)

interface UserActions{
    suspend fun registerUser(user: User): Boolean
    suspend fun attemptLogin(username: String, password: String): Boolean
}

class UserViewModel(
    private val repository: UserRepository
): ViewModel() {
    val state = repository.userFlow.map {
        LoggedUserState(
            it.email,
            it.username,
            it.profileImage,
            it.id
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = LoggedUserState()
    )

    val actions = object : UserActions {
        override suspend fun registerUser(user: User): Boolean {
            val duplicateEmailAccount = repository.findUserByEmail(user.email)
            return if (duplicateEmailAccount == null) {
                repository.upsert(user)
                true
            } else false
        }

        override suspend fun attemptLogin(username: String, password: String): Boolean =
            repository.attemptLogin(password, username)
    }
}
