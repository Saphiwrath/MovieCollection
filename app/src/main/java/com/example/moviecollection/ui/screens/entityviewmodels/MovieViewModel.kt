package com.example.moviecollection.ui.screens.entityviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.models.MovieFormat
import com.example.moviecollection.data.repositories.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class MovieState(
    val movies: List<Movie>
)

data class FavouritesState(
    val favourites: List<Int>
)

interface MovieActions {
    fun addMovie(movie: Movie): Job
    fun deleteMovie(movie: Movie): Job

    fun addMovieWithRels(
        movie: Movie,
        genres: List<Genre>,
        actors: List<Int>,
        formats: List<MovieFormat>,
        userId: Int
    ): Job

    fun getAllMoviesAndFavouritesForUser(userId: Int)

    fun removeFromFavourites(movieId: Int, userId: Int): Job
    fun addToFavourites(movieId: Int, userId: Int): Job
}

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private var _movieState: MutableStateFlow<MovieState> = MutableStateFlow(MovieState(emptyList()))
    val movieState: StateFlow<MovieState> get() = _movieState.asStateFlow()

    private var _favouriteState: MutableStateFlow<FavouritesState> = MutableStateFlow(
        FavouritesState(emptyList())
    )
    val favouritesState get() = _favouriteState.asStateFlow()

    val actions = object : MovieActions {
        override fun addMovie(movie: Movie) = viewModelScope.launch {
            repository.addMovie(movie)
        }

        override fun deleteMovie(movie: Movie) = viewModelScope.launch {
            repository.deleteMovie(movie)
        }

        override fun addMovieWithRels(
            movie: Movie,
            genres: List<Genre>,
            actors: List<Int>,
            formats: List<MovieFormat>,
            userId: Int
        ): Job = viewModelScope.launch{
            repository.addMovieWithRels(movie, genres, actors, formats, userId)
        }

        override fun getAllMoviesAndFavouritesForUser(userId: Int) {
            viewModelScope.launch {
                repository.getAllUserMovies(userId).map {
                    MovieState(it)
                }.collect { _movieState.value = it }
            }
            viewModelScope.launch {
                repository.getALlFavourites(userId).map {
                    FavouritesState(it)
                }.collect{ _favouriteState.value = it }
            }
        }

        override fun removeFromFavourites(movieId: Int, userId: Int) = viewModelScope.launch{
            repository.removeMovieFromFavourites(userId, movieId)
        }

        override fun addToFavourites(movieId: Int, userId: Int) = viewModelScope.launch{
            repository.addMovieToFavourites(userId, movieId)
        }

    }
}