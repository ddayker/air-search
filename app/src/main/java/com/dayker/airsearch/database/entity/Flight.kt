package com.dayker.airsearch.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flight")
data class Flight(
    @PrimaryKey val icao: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "depCity") val depCity: String,
    @ColumnInfo(name = "depAirport") val depAirport: String,
    @ColumnInfo(name = "depTime") val depTime: String,
    @ColumnInfo(name = "arrCity") val arrCity: String,
    @ColumnInfo(name = "arrAirport") val arrAirport: String,
    @ColumnInfo(name = "arrTime") val arrTime: String
)
