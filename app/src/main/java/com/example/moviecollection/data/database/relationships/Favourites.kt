package com.example.moviecollection.data.database.relationships

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.database.entities.User

@Entity(
    primaryKeys = ["movieId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            childColumns = arrayOf("movieId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            childColumns = arrayOf("userId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Favourites(
    @ColumnInfo val movieId: Int,
    @ColumnInfo val userId: Int
)