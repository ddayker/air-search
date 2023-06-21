package com.dayker.airsearch.model.location


import com.google.gson.annotations.SerializedName

data class GeolocationResponse(
    @SerializedName("results")
    val location: List<Place>,
    @SerializedName("status")
    val status: String
)