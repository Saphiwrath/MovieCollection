package com.example.moviecollection

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.moviecollection.data.repositories.SettingsRepository
import com.example.moviecollection.ui.screens.addmovie.AddMovieViewModel
import com.example.moviecollection.ui.screens.addwatchsession.AddWatchSessionViewModel
import com.example.moviecollection.ui.screens.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("theme")

val appModule = module {
    single { get<Context>().dataStore }

    single { SettingsRepository(get()) }

    viewModel { SettingsViewModel(get()) }

    viewModel { AddMovieViewModel() }

    viewModel { AddWatchSessionViewModel() }
}