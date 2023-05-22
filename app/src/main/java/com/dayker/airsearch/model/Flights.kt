package com.dayker.airsearch.model


import com.google.gson.annotations.SerializedName

data class Flights(
    @SerializedName("response")
    val response: List<Response>
)