package com.example.moviecollection.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.moviecollection.data.database.relationships.InFormat
import com.example.moviecollection.data.database.relationships.OfGenre
import com.example.moviecollection.data.database.relationships.WithActors
import kotlinx.coroutines.flow.Flow

@Dao
interface RelationshipsDAO {

    @Query("SELECT * FROM ofgenre")
    fun getAllOfGenre(): Flow<List<OfGenre>>

    @Query("SELECT * FROM withactors")
    fun getAllWithActors(): Flow<List<WithActors>>

    @Query("SELECT * FROM informat")
    fun getAllInFormat(): Flow<List<InFormat>>
}