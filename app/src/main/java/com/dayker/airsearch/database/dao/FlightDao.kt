package com.dayker.airsearch.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dayker.airsearch.database.entity.Flight

@Dao
interface FlightDao {

    @Query("SELECT * FROM flight")
    suspend fun getAll(): List<Flight>

    @Query("SELECT * FROM flight WHERE icao = :flightId")
    fun getFlight(flightId: String): Flight?

    @Insert
    suspend fun insert(flight: Flight)

    @Query("DELETE FROM flight WHERE icao = :flightICAO")
    suspend fun deleteFlight(flightICAO: String)

    @Query("SELECT COUNT(*) FROM flight WHERE icao = :flightICAO")
    suspend fun isFlightInBD(flightICAO: String): Int

}