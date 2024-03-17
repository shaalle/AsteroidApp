package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.AsteroidApiClient
import com.udacity.asteroidradar.api.lastDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.today
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.models.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.ArrayList


class AsteroidRepository(private val database: AsteroidDatabase) {

    suspend fun refreshAsteroids(
        startDate: String = today(),
        endDate: String = lastDay()
    ){
        var asteroidList: ArrayList<Asteroid>
        withContext(Dispatchers.IO) {
            val responseBody: ResponseBody = AsteroidApiClient.service.getAsteroids(
                startDate, endDate,
                "vOJ9Eomq4M6aQq4i9aceTERxFPecunDfou4chuKT"
            )
                .await()
            asteroidList = parseAsteroidsJsonResult(JSONObject(responseBody.string()))
            database.asteroidDao().insert(*asteroidList.asDomainModel())
        }

    }

    suspend fun deletePreviousDayAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao().deletePreviousDayAsteroids(today())
        }
    }

}