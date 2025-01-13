package com.example.moviecollection.ui.screens.addwatchsession

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.moviecollection.data.database.entities.Screening
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.util.Date

data class AddWatchSessionState(
    val image: Uri = Uri.EMPTY,
    val movieId: Int = -1,
    val place: String = "",
    val date: String = "",
    val time: String = "",
    val notes: String = ""
) {
    val canSubmit = movieId != -1
            && place.isNotBlank()
            && date.isNotBlank()
            && time.isNotBlank()
}

interface AddWatchSessionActions {
    fun addImage(image: Uri)
    fun setMovie(id: List<Int>)
    fun setPlace(place: String)
    fun setDate(date: String)
    fun setTime(time: String)
    fun setNotes(notes: String)
}

class AddWatchSessionViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddWatchSessionState())
    val state = _state.asStateFlow()

    val actions = object : AddWatchSessionActions {
        override fun addImage(image: Uri) {
            _state.update { it.copy(image = image) }
        }

        override fun setMovie(id: List<Int>) {
            _state.update { it.copy(movieId = id.last()) }
        }

        override fun setPlace(place: String) {
            _state.update { it.copy(place = place) }
        }

        override fun setDate(date: String) {
            _state.update { it.copy(date = date) }
        }

        override fun setNotes(notes: String) {
            _state.update { it.copy(notes = notes) }
        }

        override fun setTime(time: String) {
            _state.update { it.copy(time = time) }
        }
    }
}