package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.UserDAO
import com.example.moviecollection.data.database.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf

class UserRepository (
    private val userDAO: UserDAO
){
    private var _userFlow: Flow<User> = emptyFlow()
    val userFlow: Flow<User>
        get() = _userFlow

    suspend fun upsert(user: User) = userDAO.registerUser(user)

    suspend fun delete(user: User) = userDAO.deleteUser(user)

    fun findUserByEmail(email:String) = userDAO.findUserByEmail(email)

    suspend fun attemptLogin(password: String, username: String): Boolean {
        _userFlow = userDAO.attemptLogin(password, username)
        return _userFlow.firstOrNull() != null
    }

}