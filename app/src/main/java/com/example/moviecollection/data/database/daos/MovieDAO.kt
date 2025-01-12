package com.example.moviecollection.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.moviecollection.data.database.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Upsert
    suspend fun addMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM " +
            "movie LEFT JOIN registeredby ON movie.id=movieId WHERE" +
            " userId=:userId")
    fun getAllUserMovies(userId: Int): Flow<List<Movie>>

    @Query("SELECT * FROM " +
            "movie LEFT JOIN favourites ON movie.id=movieId WHERE" +
            " userId=:userId")
    fun getFavoriteMovies(userId: Int): Flow<List<Movie>>
}