package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidRadarApiService {

    @GET("api/asteroids")
    suspend fun getAsteroids(
        @Query("start_date") fromDate: String,
        @Query("end_date") toDate: String,
        @Query("api_key") apikey: String = "vOJ9Eomq4M6aQq4i9aceTERxFPecunDfou4chuKT"
    ): ResponseBody

    @GET("planetary/apod")
    fun getPicture(
        @Query("api_key") apiKey: String = "vOJ9Eomq4M6aQq4i9aceTERxFPecunDfou4chuKT"
    ): Deferred<PictureOfDay>
}

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

object AsteroidApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val service: AsteroidRadarApiService =
        retrofit.create(AsteroidRadarApiService::class.java)

}
