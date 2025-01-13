package com.example.moviecollection.data.repositories

import android.util.Log
import com.example.moviecollection.data.database.daos.MovieDAO
import com.example.moviecollection.data.database.entities.Cast
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.database.relationships.Favourites
import com.example.moviecollection.data.models.MovieFormat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class MovieRepository (
    private val movieDAO: MovieDAO
){

    suspend fun addMovie(movie: Movie) = movieDAO.addMovie(movie)

    suspend fun addMovieWithRels(
        movie: Movie,
        genres: List<Genre>,
        actors: List<Int>,
        formats: List<MovieFormat>,
        userId: Int
    ) = movieDAO.addMovieAndRels(movie, genres, actors, formats, userId)
    suspend fun deleteMovie(movie: Movie) = movieDAO.deleteMovie(movie)
    fun getAllUserMovies(userId: Int): Flow<List<Movie>> = movieDAO.getAllUserMovies(userId)

    suspend fun addMovieToFavourites(userId: Int, movieId: Int) = movieDAO.addMovieToFavourites(
        Favourites(movieId = movieId, userId = userId)
    )

    suspend fun removeMovieFromFavourites(userId: Int, movieId: Int) =
        movieDAO.removeMovieFromFavourites(
            Favourites(movieId = movieId, userId = userId)
        )

    fun getALlFavourites(userId: Int): Flow<List<Int>> = movieDAO.getFavoriteMovies(userId)
}