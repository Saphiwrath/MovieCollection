package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.MovieDAO
import com.example.moviecollection.data.database.entities.Cast
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.models.MovieFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MovieRepository (
    private val movieDAO: MovieDAO
){
    private var _movies: Flow<List<Movie>> = emptyFlow()
    val movies: Flow<List<Movie>>
            get() = _movies

    suspend fun addMovie(movie: Movie) = movieDAO.addMovie(movie)

    suspend fun addMovieWithRels(
        movie: Movie,
        genres: List<Genre>,
        actors: List<Int>,
        formats: List<MovieFormat>,
        userId: Int
    ) = movieDAO.addMovieAndRels(movie, genres, actors, formats, userId)
    suspend fun deleteMovie(movie: Movie) = movieDAO.deleteMovie(movie)
    fun getAllUserMovies(userId: Int) {
        _movies = movieDAO.getAllUserMovies(userId)
    }
}