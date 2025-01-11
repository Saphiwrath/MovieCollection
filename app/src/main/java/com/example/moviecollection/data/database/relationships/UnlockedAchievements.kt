package com.example.moviecollection.data.database.relationships

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.moviecollection.data.database.entities.Achievement
import com.example.moviecollection.data.database.entities.User

@Entity(
    primaryKeys = ["userId", "achievementId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            childColumns = arrayOf("userId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Achievement::class,
            childColumns = arrayOf("achievementId"),
            parentColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UnlockedAchievements(
    @ColumnInfo val userId: Int,
    @ColumnInfo val achievementId: Int
)