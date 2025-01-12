package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.GenreDAO
import com.example.moviecollection.data.database.entities.Genre

class GenreRepository(
    private val genreDAO: GenreDAO
) {
    val genres = genreDAO.getAllGenres()

    suspend fun addGenre(genre: Genre) = genreDAO.addGenre(genre)

    fun findDuplicate(name: String) = genreDAO.findDuplicate(name)
}