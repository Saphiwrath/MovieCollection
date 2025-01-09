package com.example.moviecollection.ui.screens.addmovie

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.moviecollection.data.models.MovieFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddMovieState(
    var title: String = "",
    var year: Int = 2000,
    var director: Int = -1,
    var actors: List<Int> = emptyList(),
    var genres: List<String> = emptyList(),
    var format: List<MovieFormat> = emptyList(),
    var image: Uri = Uri.EMPTY
)

interface AddMovieActions {
    fun setTitle(title: String)
    fun setYear(year: Int)
    fun setDirector(director: List<Int>)
    fun setActors(actors: List<Int>)
    fun setGenres(genres: List<String>)
    fun setFormat(format: List<MovieFormat>)
    fun addImage(image: Uri)
}

class AddMovieViewModel: ViewModel() {
    private val _state = MutableStateFlow(AddMovieState())
    val state = _state.asStateFlow()

    val actions = object : AddMovieActions {
        override fun setTitle(title: String) {
            _state.update { it.copy(title = title) }
        }

        override fun setYear(year: Int) {
            _state.update { it.copy(year = year) }
        }

        override fun setDirector(director: List<Int>) {
            _state.update { it.copy(director = director.first()) }
        }

        override fun setActors(actors: List<Int>) {
            _state.update { it.copy(actors = actors) }
        }

        override fun setGenres(genres: List<String>) {
            _state.update { it.copy(genres = genres) }
        }

        override fun setFormat(format: List<MovieFormat>) {
            _state.update { it.copy(format = format) }
        }

        override fun addImage(image: Uri) {
            _state.update { it.copy(image = image) }
        }

    }
}