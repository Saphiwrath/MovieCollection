package com.example.moviecollection.data.database.relationships

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.moviecollection.data.database.entities.Format
import com.example.moviecollection.data.database.entities.Movie

@Entity(
    primaryKeys = ["movieId", "format"],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            childColumns = arrayOf("movieId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Format::class,
            childColumns = arrayOf("format"),
            parentColumns = arrayOf("type"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class InFormat(
    @ColumnInfo val movieId: Int,
    @ColumnInfo val format: String
)
