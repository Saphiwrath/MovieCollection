package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.UserDAO
import com.example.moviecollection.data.database.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRepository (
    private val userDAO: UserDAO
){
    var loggedUser: Flow<User> = flowOf()

    suspend fun upsert(user: User) = userDAO.registerUser(user)

    suspend fun delete(user: User) = userDAO.deleteUser(user)

    fun findUserByEmail(email:String) = userDAO.findUserByEmail(email)

    fun attemptLogin(password: String, username: String) = userDAO.attemptLogin(password, username)
}