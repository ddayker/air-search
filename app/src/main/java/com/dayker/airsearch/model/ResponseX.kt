package com.dayker.airsearch.model


import com.google.gson.annotations.SerializedName

data class ResponseX(

    @SerializedName("airline_name")
    val airlineName: String,
    @SerializedName("arr_city")
    val arrCity: String,
    @SerializedName("arr_country")
    val arrCountry: String,
    @SerializedName("arr_icao")
    val arrIcao: String,
    @SerializedName("arr_name")
    val arrName: String,
    @SerializedName("arr_terminal")
    val arrTerminal: String,
    @SerializedName("arr_time")
    val arrTime: String,
    @SerializedName("dep_time")
    val depTime: String,
    @SerializedName("arr_actual")
    val arrActualTime: String,
    @SerializedName("dep_actual")
    val depActualTime: String,
    @SerializedName("dep_city")
    val depCity: String,
    @SerializedName("dep_country")
    val depCountry: String,
    @SerializedName("dep_icao")
    val depIcao: String,
    @SerializedName("dep_name")
    val depName: String,
    @SerializedName("dep_terminal")
    val depTerminal: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("flight_icao")
    val flightIcao: String,
    @SerializedName("flight_number")
    val flightNumber: String,
    @SerializedName("reg_number")
    val regNumber: String,
    @SerializedName("status")
    val status: String
)