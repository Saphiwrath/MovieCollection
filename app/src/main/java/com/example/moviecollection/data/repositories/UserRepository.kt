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
    private var _userFlow: Flow<User?> = emptyFlow()
    val userFlow: Flow<User?>
        get() = _userFlow

    suspend fun upsert(user: User) = userDAO.registerUser(user)

    suspend fun updateUsername(username: String, id: Int) = userDAO.updateUsername(username, id)

    suspend fun delete(user: User) = userDAO.deleteUser(user)

    private fun getUser(userId: Int) = userDAO.getUser(userId)

    fun findUserByEmail(email:String) = userDAO.findUserByEmail(email)

    // Every time you change username or password it makes the whole app crash because
    // this now returns false methinks, which means userFlow is now null
    // SOLVED but leaving this here as a reminder of my hybris
    suspend fun attemptLogin(password: String, username: String): Boolean {
        val foundUser = userDAO.attemptLogin(password, username).firstOrNull()
        return if (foundUser != null) {
            _userFlow = getUser(foundUser.id)
            true
        } else false
    }

}