package com.dayker.airsearch.model.flight


import com.google.gson.annotations.SerializedName

data class ActualFlight(
    @SerializedName("arr_icao")
    val arrIcao: String,
    @SerializedName("dep_icao")
    val depIcao: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("flight_icao")
    val flightIcao: String,
    @SerializedName("hex")
    val hex: String,
    @SerializedName("reg_number")
    val regNumber: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("speed")
    val speed: Int
)