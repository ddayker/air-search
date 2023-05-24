package com.dayker.airsearch.model


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("aircraft_icao")
    val aircraftIcao: String,
    @SerializedName("airline_icao")
    val airlineIcao: String,
    @SerializedName("alt")
    val alt: Int,
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
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("reg_number")
    val regNumber: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("speed")
    val speed: Int
)