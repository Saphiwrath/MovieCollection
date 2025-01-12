package com.example.moviecollection

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.moviecollection.data.database.MovieCollectionDatabase
import com.example.moviecollection.data.database.entities.Genre
import com.example.moviecollection.data.repositories.CastRepository
import com.example.moviecollection.data.repositories.GenreRepository
import com.example.moviecollection.data.repositories.MovieRepository
import com.example.moviecollection.data.repositories.SettingsRepository
import com.example.moviecollection.data.repositories.UserRepository
import com.example.moviecollection.ui.screens.addmovie.AddMovieViewModel
import com.example.moviecollection.ui.screens.addwatchsession.AddWatchSessionViewModel
import com.example.moviecollection.ui.screens.dbviewmodels.CastViewModel
import com.example.moviecollection.ui.screens.dbviewmodels.GenreViewModel
import com.example.moviecollection.ui.screens.dbviewmodels.MovieViewModel
import com.example.moviecollection.ui.screens.dbviewmodels.UserViewModel
import com.example.moviecollection.ui.screens.login.LoginViewModel
import com.example.moviecollection.ui.screens.settings.SettingsViewModel
import com.example.moviecollection.ui.screens.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("theme")

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

    // DATABASE

    single {
        Room.databaseBuilder(
            get(),
            MovieCollectionDatabase::class.java,
            "movie_collection"
        ).build()
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
}