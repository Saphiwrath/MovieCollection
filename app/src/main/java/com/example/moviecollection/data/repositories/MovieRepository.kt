package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.MovieDAO
import com.example.moviecollection.data.database.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MovieRepository (
    private val movieDAO: MovieDAO
){
    private var _movies: Flow<List<Movie>> = emptyFlow()
    val movies: Flow<List<Movie>>
            get() = _movies

    suspend fun addMovie(movie: Movie) = movieDAO.addMovie(movie)
    suspend fun deleteMovie(movie: Movie) = movieDAO.deleteMovie(movie)
    fun getAllUserMovies(userId: Int) {
        _movies = movieDAO.getAllUserMovies(userId)
    }
}