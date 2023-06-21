package com.dayker.airsearch.model.country


import com.google.gson.annotations.SerializedName

data class CountriesResponse(
    @SerializedName("response")
    val countries: List<Country>
)