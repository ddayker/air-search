package com.dayker.airsearch.network

import com.dayker.airsearch.model.FlightInfo
import com.dayker.airsearch.model.Flights
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("flights")
    suspend fun getFlights(
        @Query("api_key") apiKey: String,
        @Query("flag") region: String,
    ): Flights

    @GET("flight")
    suspend fun getFlightInfo(
        @Query("flight_icao") icao: String,
        @Query("api_key") apiKey: String
    ): FlightInfo
}