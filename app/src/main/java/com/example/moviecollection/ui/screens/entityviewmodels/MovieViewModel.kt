package com.example.moviecollection.ui.screens.entityviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.models.MovieFormat
import com.example.moviecollection.data.repositories.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MovieState(
    val movies: List<Movie>
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

    fun getAllMoviesForUser(userId: Int)

}

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private var _state: MutableStateFlow<MovieState> = MutableStateFlow(MovieState(emptyList()))
    val state: StateFlow<MovieState> get() = _state.asStateFlow()

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

        override fun getAllMoviesForUser(userId: Int) {
            viewModelScope.launch {
                repository.getAllUserMovies(userId).map { movies ->
                    MovieState(movies)
                }.collect { movieState ->
                    _state.value = movieState
                }
            }
        }

    }
}