package com.example.moviecollection

import android.content.Context
import android.location.LocationManager
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.moviecollection.data.database.MovieCollectionDatabase
import com.example.moviecollection.data.models.MovieFormat
import com.example.moviecollection.data.models.AchievementName
import com.example.moviecollection.data.models.AchievementType
import com.example.moviecollection.data.remote.OSMDataSource
import com.example.moviecollection.data.repositories.AchievementRepository
import com.example.moviecollection.data.repositories.CastRepository
import com.example.moviecollection.data.repositories.GenreRepository
import com.example.moviecollection.data.repositories.MovieRepository
import com.example.moviecollection.data.repositories.RelationshipsRepository
import com.example.moviecollection.data.repositories.ScreeningRepository
import com.example.moviecollection.data.repositories.SettingsRepository
import com.example.moviecollection.data.repositories.UserRepository
import com.example.moviecollection.ui.screens.addmovie.AddMovieViewModel
import com.example.moviecollection.ui.screens.addwatchsession.AddWatchSessionViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.AchievementViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.CastViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.GenreViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.MovieViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.RelationshipsViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.ScreeningViewModel
import com.example.moviecollection.ui.screens.entityviewmodels.UserViewModel
import com.example.moviecollection.ui.screens.login.LoginViewModel
import com.example.moviecollection.ui.screens.settings.SettingsViewModel
import com.example.moviecollection.ui.screens.signup.SignupViewModel
import com.example.moviecollection.utils.LocationService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        AchievementName.entries.forEach {
            db.execSQL("INSERT INTO achievement (name, condition) VALUES (?, ?)",
                arrayOf(it.toString(), ""))
        }
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        AchievementName.entries.forEach {
            val table = when (it.type) {
                AchievementType.Movie -> "registeredby"
                AchievementType.Screening -> "screening"
            }
            db.execSQL("""
                CREATE TRIGGER ${it.name}_trigger
                AFTER INSERT ON $table
                WHEN (SELECT COUNT(*) FROM $table WHERE userId = NEW.userId) = ${it.number}
                BEGIN
                    INSERT INTO unlockedachievements (userId, achievementId)
                    VALUES (NEW.userId, (SELECT id FROM achievement WHERE name = '${it.name}'));
                END;
                """.trimIndent()
            )
        }
    }
}

val appModule = module {
    single { get<Context>().dataStore }

    single { SettingsRepository(get()) }

    single { LocationService(androidContext()) }

    // JSON and OSM
    single {
        HttpClient {
            install(ContentNegotiation) {
                json (Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    single { OSMDataSource(get()) }

    // Camera


    // ViewModels
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

    viewModel { AchievementViewModel(get()) }

    // DATABASE
    single {
        Room.databaseBuilder(
            get(),
            MovieCollectionDatabase::class.java,
            "movie_collection"
        )
        .addMigrations(
            MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4
        )
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
    single {
        AchievementRepository(get<MovieCollectionDatabase>().achievementDAO())
    }
}