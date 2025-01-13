package com.example.moviecollection

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.moviecollection.data.database.MovieCollectionDatabase
import com.example.moviecollection.data.models.MovieFormat
import com.example.moviecollection.data.repositories.CastRepository
import com.example.moviecollection.data.repositories.GenreRepository
import com.example.moviecollection.data.repositories.MovieRepository
import com.example.moviecollection.data.repositories.RelationshipsRepository
import com.example.moviecollection.data.repositories.ScreeningRepository
import com.example.moviecollection.data.repositories.SettingsRepository
import com.example.moviecollection.data.repositories.UserRepository
import com.example.moviecollection.ui.screens.addmovie.AddMovieViewModel
import com.example.moviecollection.ui.screens.addwatchsession.AddWatchSessionViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.CastViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.GenreViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.MovieViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.RelationshipsViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.ScreeningViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.UserViewModel
import com.example.moviecollection.ui.screens.login.LoginViewModel
import com.example.moviecollection.ui.screens.settings.SettingsViewModel
import com.example.moviecollection.ui.screens.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("theme")

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        MovieFormat.entries.forEach {
            db.execSQL("INSERT INTO format (type) VALUES (?)", arrayOf(it.toString()))
        }
    }
}

val appModule = module {
    single { get<Context>().dataStore }

    single { SettingsRepository(get()) }

    viewModel { SettingsViewModel(get()) }

    viewModel { AddMovieViewModel() }

    viewModel { AddWatchSessionViewModel() }

    viewModel { LoginViewModel() }

    viewModel { SignupViewModel() }

    viewModel { UserViewModel(get()) }

    viewModel { MovieViewModel(get()) }

    viewModel { CastViewModel(get()) }

    viewModel { GenreViewModel(get()) }

    viewModel { ScreeningViewModel(get()) }

    viewModel { RelationshipsViewModel(get()) }

    // DATABASE
    single {
        Room.databaseBuilder(
            get(),
            MovieCollectionDatabase::class.java,
            "movie_collection"
        )
        .addMigrations(MIGRATION_1_2)
        .build()
    }

    // REPOSITORIES WITH DAOs
    single {
        UserRepository(get<MovieCollectionDatabase>().userDAO())
    }
    single {
        MovieRepository(get<MovieCollectionDatabase>().movieDAO())
    }
    single {
        CastRepository(get<MovieCollectionDatabase>().castDAO())
    }
    single {
        GenreRepository(get<MovieCollectionDatabase>().genreDAO())
    }
    single {
        ScreeningRepository(get<MovieCollectionDatabase>().screeningDAO())
    }
    single {
        RelationshipsRepository(get<MovieCollectionDatabase>().relationshipsDAO())
    }
}