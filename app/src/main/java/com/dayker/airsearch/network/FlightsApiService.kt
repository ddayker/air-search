package com.dayker.airsearch.network

import com.dayker.airsearch.model.country.CountriesResponse
import com.dayker.airsearch.model.flight.ActualFlightResponse
import com.dayker.airsearch.model.info.FlightInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightsApiService {
    @GET("flights")
    suspend fun getFlightsWithRegion(
        @Query("api_key") apiKey: String,
        @Query("flag") region: String,
    ): ActualFlightResponse

    @GET("flights")
    suspend fun getFlights(
        @Query("api_key") apiKey: String
    ): ActualFlightResponse

    @GET("flight")
    suspend fun getFlightInfo(
        @Query("flight_icao") icao: String,
        @Query("api_key") apiKey: String
    ): FlightInfoResponse

    @GET("countries")
    suspend fun getCountries(
        @Query("api_key") apiKey: String
    ): CountriesResponse
}