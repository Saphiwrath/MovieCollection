package com.example.moviecollection.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Format(
    @PrimaryKey val type: String
)