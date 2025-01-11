package com.example.moviecollection.data.database.relationships

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.moviecollection.data.database.entities.Cast
import com.example.moviecollection.data.database.entities.Movie

@Entity(
    primaryKeys = ["movieId", "castId"],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            childColumns = arrayOf("movieId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Cast::class,
            childColumns = arrayOf("castId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WithActors(
    @ColumnInfo val movieId: Int,
    @ColumnInfo val castId: Int
)