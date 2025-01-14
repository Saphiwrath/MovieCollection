package com.example.moviecollection.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.moviecollection.data.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM user WHERE password=:password AND username=:username")
    fun attemptLogin(password: String, username: String) : Flow<User>

    @Query("UPDATE user SET username=:username WHERE id=:id")
    suspend fun updateUsername(username: String, id: Int)

    @Query("SELECT * FROM user WHERE id=:userId")
    fun getUser(userId: Int): Flow<User>

    @Upsert
    suspend fun registerUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE email=:email")
    fun findUserByEmail(email: String) : User?
}