package com.example.moviecollection.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Cast::class,
            childColumns = arrayOf("directorId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int  = 0,
    @ColumnInfo val poster: String,
    @ColumnInfo val title: String,
    @ColumnInfo val year: Int,
    @ColumnInfo val notes: String,
    @ColumnInfo val directorId: Int
)