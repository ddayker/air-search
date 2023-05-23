package com.dayker.airsearch.model


import com.google.gson.annotations.SerializedName

data class FlightInfo(
    @SerializedName("response")
    val response: ResponseX
)