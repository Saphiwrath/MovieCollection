package com.example.moviecollection.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.moviecollection.data.database.entities.Cast
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.database.relationships.InFormat
import com.example.moviecollection.data.database.relationships.OfGenre
import com.example.moviecollection.data.database.relationships.RegisteredBy
import com.example.moviecollection.data.database.relationships.WithActors
import com.example.moviecollection.data.models.MovieFormat
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDAO {

    suspend fun addMovieAndRels(
        movie: Movie,
        genres: List<Genre>,
        actors: List<Int>,
        formats: List<MovieFormat>,
        userId: Int
    ) {
        val movieId = addMovie(movie).toInt()
        addRegisteredBy(RegisteredBy(movieId, userId))
        genres.forEach { addOfGenre(OfGenre(movieId, it.name)) }
        actors.forEach { addWithActors(WithActors(movieId, it)) }
/*        formats.forEach{ addInFormat(InFormat(movieId, it.toString())) }*/
    }

    // START of upsert queries for movie addition. Delete queries should be
    // handled by database creation through the foreign key constructors
    @Upsert
    abstract suspend fun addMovie(movie: Movie): Long
    @Upsert
    abstract suspend fun addRegisteredBy(registeredBy: RegisteredBy)
    @Upsert
    abstract suspend fun addOfGenre(ofGenre: OfGenre)
    @Upsert
    abstract suspend fun addWithActors(withActors: WithActors)
    @Upsert
    abstract suspend fun addInFormat(inFormat: InFormat)
    //END of upsert queries for movie addition

    @Delete
    abstract suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM " +
            "movie LEFT JOIN registeredby ON movie.id=movieId WHERE" +
            " userId=:userId")
    abstract fun getAllUserMovies(userId: Int): Flow<List<Movie>>

    @Query("SELECT * FROM " +
            "movie LEFT JOIN favourites ON movie.id=movieId WHERE" +
            " userId=:userId")
    abstract fun getFavoriteMovies(userId: Int): Flow<List<Movie>>
}