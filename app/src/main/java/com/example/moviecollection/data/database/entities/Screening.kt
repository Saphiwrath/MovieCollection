package com.example.moviecollection.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
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
data class Screening(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val userId: Int,
    @ColumnInfo val movieId: Int,
    @ColumnInfo val place: String,
    @ColumnInfo val date: String,
    @ColumnInfo val time: String,
    @ColumnInfo val notes: String,
    @ColumnInfo val image: String,
)