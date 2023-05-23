package com.dayker.airsearch.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dayker.airsearch.database.entity.Flight

@Dao
interface FlightDao {

    @Query("SELECT * FROM flight")
    suspend fun getAll(): List<Flight>

    @Insert
    suspend fun insert(flight: Flight)

    @Query("DELETE FROM flight WHERE icao = :flightICAO")
    suspend fun deleteFlight(flightICAO: String)

}