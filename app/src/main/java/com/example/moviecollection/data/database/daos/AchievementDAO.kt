package com.example.moviecollection.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.moviecollection.data.database.entities.Achievement
import com.example.moviecollection.data.database.relationships.UnlockedAchievements
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDAO {
    @Upsert
    suspend fun upsert(unlockedAchievements: UnlockedAchievements)

    @Query("SELECT * FROM achievement WHERE id IN " +
            "(SELECT achievementId FROM unlockedachievements " +
            "WHERE userId=:userId)")
    fun getAllUserAchievements(userId: Int) : Flow<List<Achievement>>

    @Query("SELECT * FROM achievement WHERE id NOT IN " +
            "(SELECT achievementId FROM unlockedachievements " +
            "WHERE userId=:userId)")
    fun getNotUnlockedAchievements(userId: Int) : Flow<List<Achievement>>
}