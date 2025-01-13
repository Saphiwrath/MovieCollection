package com.example.moviecollection.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviecollection.data.database.daos.CastDAO
import com.example.moviecollection.data.database.daos.GenreDAO
import com.example.moviecollection.data.database.daos.MovieDAO
import com.example.moviecollection.data.database.daos.UserDAO
import com.example.moviecollection.data.database.entities.Achievement
import com.example.moviecollection.data.database.entities.Cast
import com.example.moviecollection.data.database.entities.Format
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.database.entities.Movie
import com.example.moviecollection.data.database.entities.Screening
import com.example.moviecollection.data.database.entities.User
import com.example.moviecollection.data.database.relationships.Favourites
import com.example.moviecollection.data.database.relationships.InFormat
import com.example.moviecollection.data.database.relationships.OfGenre
import com.example.moviecollection.data.database.relationships.RegisteredBy
import com.example.moviecollection.data.database.relationships.UnlockedAchievements
import com.example.moviecollection.data.database.relationships.WithActors

@Database(
    entities = [
        Achievement::class,
        Cast::class,
        Format::class,
        Genre::class,
        Movie::class,
        Screening::class,
        User::class,
        Favourites::class,
        InFormat::class,
        OfGenre::class,
        RegisteredBy::class,
        UnlockedAchievements::class,
        WithActors::class
    ],
    version = 2
)
abstract class MovieCollectionDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    abstract fun movieDAO(): MovieDAO

    abstract fun castDAO(): CastDAO

    abstract fun genreDAO(): GenreDAO
}