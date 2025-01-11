package com.example.moviecollection.ui.screens.dbviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.User
import com.example.moviecollection.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class LoggedUserState(
    val email: String = "",
    val username: String = "",
    val image: String? = null
)

interface UserActions{
    suspend fun registerUser(user: User): Boolean
    fun attemptLogin(username: String, password: String): Job
}

class UserViewModel(
    private val repository: UserRepository
): ViewModel() {
    val state = repository.loggedUser.map {
        LoggedUserState(
            it.email,
            it.username,
            it.profileImage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = LoggedUserState()
    )

    val actions = object : UserActions {
        override suspend fun registerUser(user: User): Boolean = withContext(Dispatchers.IO) {
            val duplicateEmailAccount = repository.findUserByEmail(user.email)
            if (duplicateEmailAccount == null) {
                repository.upsert(user)
                return@withContext true
            } else return@withContext false

        }

        override fun attemptLogin(username: String, password: String): Job {
            TODO("Not yet implemented")
        }
    }
}
