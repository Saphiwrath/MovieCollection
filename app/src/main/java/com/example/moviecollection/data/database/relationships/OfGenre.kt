package com.example.moviecollection.data.database.relationships

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.database.entities.Movie

@Entity(
    primaryKeys = ["movieId", "genre"],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            childColumns = arrayOf("movieId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Genre::class,
            childColumns = arrayOf("genre"),
            parentColumns = arrayOf("name"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OfGenre(
    @ColumnInfo val movieId: Int,
    @ColumnInfo val genre: String
)