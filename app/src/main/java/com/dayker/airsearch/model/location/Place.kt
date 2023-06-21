package com.dayker.airsearch.model.location


import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("geometry")
    val geolocation: Geolocation,
    @SerializedName("place_id")
    val placeId: String
)