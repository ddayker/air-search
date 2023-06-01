package com.dayker.airsearch.model


import com.google.gson.annotations.SerializedName

data class FlightInfoResponse(
    @SerializedName("response")
    val response: FlightInfo
)