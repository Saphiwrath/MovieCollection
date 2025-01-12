package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.CastDAO
import com.example.moviecollection.data.database.entities.Cast

class CastRepository (
    private val castDAO: CastDAO
) {
    val directors = castDAO.getDirectors()
    val actors = castDAO.getActors()

    suspend fun addCast(cast: Cast) = castDAO.addCast(cast)
}