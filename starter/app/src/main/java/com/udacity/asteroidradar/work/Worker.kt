package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.load.HttpException
import com.udacity.asteroidradar.api.lastDay
import com.udacity.asteroidradar.api.today
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getDatabase

class Worker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        /* Refresh the data from network and save it to database, also refreshes the picture of
         the day from network and save it to database */
        return try {
            repository.refreshAsteroids(today(), lastDay())
            repository.refreshPictureOfDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}