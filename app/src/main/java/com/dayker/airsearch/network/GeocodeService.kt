package com.dayker.airsearch.network

import com.dayker.airsearch.model.location.GeolocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeService {
    @GET("geocode/json")
    suspend fun getLocation(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): GeolocationResponse
}