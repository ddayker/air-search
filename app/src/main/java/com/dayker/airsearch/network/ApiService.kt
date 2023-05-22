package com.dayker.airsearch.network

import com.dayker.airsearch.model.Flights
import com.dayker.airsearch.model.Response
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("flights")
    suspend fun getFlights(@Query("api_key") apiKey: String): Flights

}