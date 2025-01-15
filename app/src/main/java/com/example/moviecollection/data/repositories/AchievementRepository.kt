package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.AchievementDAO
import com.example.moviecollection.data.database.entities.Achievement
import com.example.moviecollection.data.database.relationships.UnlockedAchievements
import kotlinx.coroutines.flow.Flow

class AchievementRepository (
    private val achievementDAO: AchievementDAO
) {
    suspend fun unlockAchievement(userId: Int, achievementId: Int) =
        achievementDAO.upsert(UnlockedAchievements(userId, achievementId))

    fun getAllUserAchievements(userId: Int): Flow<List<Achievement>> =
        achievementDAO.getAllUserAchievements(userId)

    fun getNotUnlockedAchievements(userId: Int): Flow<List<Achievement>> =
        achievementDAO.getNotUnlockedAchievements(userId)
}