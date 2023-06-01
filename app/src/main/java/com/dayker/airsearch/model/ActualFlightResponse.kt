package com.dayker.airsearch.model


import com.google.gson.annotations.SerializedName

data class ActualFlightResponse(
    @SerializedName("response")
    val response: List<ActualFlight>
)