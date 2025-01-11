package com.example.moviecollection

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.moviecollection.data.database.MovieCollectionDatabase
import com.example.moviecollection.data.repositories.SettingsRepository
import com.example.moviecollection.data.repositories.UserRepository
import com.example.moviecollection.ui.screens.addmovie.AddMovieViewModel
import com.example.moviecollection.ui.screens.addwatchsession.AddWatchSessionViewModel
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
}