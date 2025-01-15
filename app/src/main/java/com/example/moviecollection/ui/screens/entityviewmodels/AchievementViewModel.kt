package com.example.moviecollection.ui.screens.entityviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.Achievement
import com.example.moviecollection.data.repositories.AchievementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class UnlockedAchievementState(
    val achievements: List<Achievement>
)

data class LockedAchievementsState(
    val achievements: List<Achievement>
)

interface AchievementActions {
    fun getAllAchievements(userId: Int)
}

class AchievementViewModel(
    repository: AchievementRepository
): ViewModel() {
    private var _unlockedAchievementsState = MutableStateFlow<UnlockedAchievementState>(
        UnlockedAchievementState(emptyList())
    )
    val unlockedAchievementsState get() = _unlockedAchievementsState.asStateFlow()

    private var _lockedAchievementsState = MutableStateFlow<LockedAchievementsState>(
        LockedAchievementsState(emptyList())
    )
    val lockedAchievementsState get() = _lockedAchievementsState.asStateFlow()

    val actions = object : AchievementActions {
        override fun getAllAchievements(userId: Int) {
            viewModelScope.launch {
                repository.getAllUserAchievements(userId).map { UnlockedAchievementState(it) }
                    .collect{ _unlockedAchievementsState.value = it }
            }
            viewModelScope.launch {
                repository.getNotUnlockedAchievements(userId).map {
                    LockedAchievementsState(it)
                }.collect{ _lockedAchievementsState.value = it }
            }
        }

    }
}