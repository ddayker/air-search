package com.dayker.airsearch.model.location


import com.google.gson.annotations.SerializedName

data class Geolocation(
    @SerializedName("location")
    val coordinates: Coordinates
)