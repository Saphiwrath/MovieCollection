package com.example.moviecollection.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.moviecollection.data.database.entities.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDAO {
    @Upsert
    suspend fun addGenre(genre: Genre)

    @Delete
    suspend fun deleteGenre(genre: Genre)

    @Query("SELECT * FROM genre WHERE name=:name")
    fun findDuplicate(name: String): Boolean

    @Query("SELECT * FROM genre")
    fun getAllGenres(): Flow<List<Genre>>
}