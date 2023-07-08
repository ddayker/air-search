package com.dayker.airsearch.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dayker.airsearch.database.ProjectDatabase
import com.dayker.airsearch.database.entity.Flight
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FlightDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ProjectDatabase
    private lateinit var dao: FlightDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProjectDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.flightDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertFlight() = runTest {
        val newFlight = Flight(
            "icao",
            "company",
            "depCity",
            "depAir",
            "depTime",
            "arrCity",
            "arrAir",
            "arrTime"
        )
        dao.insert(newFlight)
        val allFlights = dao.getAll()
        assertThat(allFlights).contains(newFlight)
    }

    @Test
    fun deleteFlight() = runTest {
        val newFlight = Flight(
            "icao",
            "company",
            "depCity",
            "depAir",
            "depTime",
            "arrCity",
            "arrAir",
            "arrTime"
        )
        dao.insert(newFlight)
        dao.deleteFlight(newFlight.icao)
        val allFlights = dao.getAll()
        assertThat(allFlights).doesNotContain(newFlight)
    }

    @Test
    fun isFlightInBD() = runTest {
        val newFlight = Flight(
            "icao",
            "company",
            "depCity",
            "depAir",
            "depTime",
            "arrCity",
            "arrAir",
            "arrTime"
        )
        dao.insert(newFlight)
        val count = dao.isFlightInBD(newFlight.icao)
        assertThat(count).isAtLeast(1)
    }
}