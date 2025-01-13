package com.example.moviecollection.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.moviecollection.data.database.entities.Screening
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreeningDAO {

    @Upsert
    suspend fun upsert(screening: Screening)

    @Delete
    suspend fun delete(screening: Screening)

    @Query("SELECT * FROM screening WHERE userId=:userId")
    fun getAllUserScreenings(userId: Int): Flow<List<Screening>>
}