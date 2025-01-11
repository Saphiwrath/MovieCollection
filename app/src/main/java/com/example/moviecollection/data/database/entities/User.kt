package com.example.moviecollection.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val profileImage: String?,
    @ColumnInfo val username: String,
    @ColumnInfo val email: String,
    @ColumnInfo val password: String
)