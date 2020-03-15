package com.example.themoviedb

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.example.themoviedb.database.TheMovieDBDatabase
import com.example.themoviedb.network.Api
import com.example.themoviedb.network.TheMovieDBFactory
import com.example.themoviedb.network.TheMovieDBRetrofit
import com.example.themoviedb.network.repository.LibraryRepository
import com.example.themoviedb.network.repository.MovieRepository
import com.example.themoviedb.network.repository.TvRepository
import com.example.themoviedb.ui.library.LibraryViewModel
import com.example.themoviedb.ui.movies.detail.DetailMoviesViewModel
import com.example.themoviedb.ui.movies.home.HomeMoviesViewModel
import com.example.themoviedb.ui.tv.detail.DetailTvViewModel
import com.example.themoviedb.ui.tv.home.HomeTvViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TheMovieDB : Application() {

    private val databaseModule = module {
        single {
            Room.databaseBuilder(
                applicationContext,
                TheMovieDBDatabase::class.java,
                "Database"
            ).build()
        }
    }

    private val networkModule = module {
        single { TheMovieDBRetrofit.build() }
        single { TheMovieDBFactory.build(get()) }
        single { Api(get()) }
    }

    private val repositoryModule = module {
        single { MovieRepository(get()) }
        single { TvRepository(get()) }
        single { LibraryRepository(get()) }
    }

    private val viewModelModule = module {
        viewModel { HomeMoviesViewModel(get()) }
        viewModel { HomeTvViewModel(get()) }
        viewModel { DetailMoviesViewModel(get(), get()) }
        viewModel { DetailTvViewModel(get(), get()) }
        viewModel { LibraryViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(
                databaseModule,
                networkModule,
                repositoryModule,
                viewModelModule
            ))
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "The Movie Db"
            val descriptionText = "Latest"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("The Movie Db", name, importance)
                .apply {
                    description = descriptionText
                }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}