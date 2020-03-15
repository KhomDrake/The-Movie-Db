package com.example.themoviedb.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.themoviedb.R
import com.example.themoviedb.network.repository.MovieRepository
import com.example.themoviedb.network.repository.TvRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

class LatestWork(private val appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams),
    KoinComponent {

    private val movieRepository: MovieRepository by inject()
    private val tvRepository: TvRepository by inject()

    override fun doWork(): Result {
        runBlocking(Dispatchers.Default) {
            val chance = (0..10).random()
            if(chance > 5) {
                val tv = tvRepository.latestTv()
                makeNotification(
                    appContext,
                    tv.originalName,
                    R.drawable.ic_tv_black_24dp
                )
            } else {
                val movie = movieRepository.movieLatest()
                makeNotification(
                    appContext,
                    movie.originalTitle,
                    R.drawable.ic_cinema_black_24dp
                )
            }
        }

        return Result.success()
    }

    private fun makeNotification(context: Context, title: String?, icon: Int) {
        runBlocking(Dispatchers.Main) {
            val builder = NotificationCompat.Builder(context, "The Movie Db")
                .apply {
                    setSmallIcon(icon)
                    setContentTitle("The Movie Db Notification")
                    setContentText(title ?: "Sem Titulo ainda")
                    priority = NotificationCompat.PRIORITY_HIGH
                    setAutoCancel(true)
                }

            with(NotificationManagerCompat.from(context)) {
                notify(1, builder.build())
            }
        }
    }
}