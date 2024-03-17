package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.models.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: AsteroidEntity)

    @Query("SELECT * FROM asteroid Where closeApproachDate >= :sDate AND closeApproachDate <= :eDate ORDER BY closeApproachDate ASC")
    suspend fun getAsteroids(sDate: String, eDate: String): Flow<List<Asteroid>>

}

