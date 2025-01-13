package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.ScreeningDAO
import com.example.moviecollection.data.database.entities.Screening
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class ScreeningRepository (
    private val screeningDAO: ScreeningDAO
) {
    private var _screenings: Flow<List<Screening>> = emptyFlow()
    val screenings: Flow<List<Screening>>
        get() = _screenings

    suspend fun addScreening(screening: Screening) = screeningDAO.upsert(screening)
    suspend fun deleteScreening(screening: Screening) = screeningDAO.delete(screening)
    fun getAllScreeningsForUser(userId: Int) = screeningDAO.getAllUserScreenings(userId)
}