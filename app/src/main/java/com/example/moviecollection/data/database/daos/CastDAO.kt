package com.example.moviecollection.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.moviecollection.data.database.entities.Cast
import kotlinx.coroutines.flow.Flow

@Dao
interface CastDAO {
    @Upsert
    suspend fun addCast(cast: Cast)

    @Query("SELECT * FROM `cast` WHERE isActor!=0")
    fun getActors(): Flow<List<Cast>>

    @Query("SELECT * FROM `cast` WHERE isDirector!=0")
    fun getDirectors(): Flow<List<Cast>>
}