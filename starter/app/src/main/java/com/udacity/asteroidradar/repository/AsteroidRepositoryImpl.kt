package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.AsteroidRadarApiService
import com.udacity.asteroidradar.models.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


//class AsteroidRepositoryImpl(
//    private val api: AsteroidRadarApiService
//) : AsteroidRepository {
//
//    override suspend fun getAsteroids(
//        startDate: String,
//        endDate: String
//    ) = api.getAsteroids(startDate, endDate)
//
//    override suspend fun getPicture(date: String) = api.getPicture(date)
//
//    override suspend fun cacheAsteroids(response: ResponseBody) {
//// parse response and cache asteroids locally
//    }
//
//}

class AsteroidRepositoryImpl(
    private val api: AsteroidRadarApiService
) : AsteroidRepository {

    override suspend fun getAsteroids(): Result<List<Asteroid>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getAsteroids()
                val asteroids = parseAsteroids(response)
                cacheAsteroids(asteroids)
                Result.success(asteroids)
            } catch(e: Exception) {
                Result.failure(e)
            }
        }

    private suspend fun cacheAsteroids(asteroids: List<Asteroid>) {
        withContext(Dispatchers.IO) {
// save to database
        }
    }

}