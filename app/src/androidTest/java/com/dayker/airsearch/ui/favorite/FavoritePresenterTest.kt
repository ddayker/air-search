package com.dayker.airsearch.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dayker.airsearch.database.ProjectDatabase
import com.dayker.airsearch.database.dao.FlightDao
import com.dayker.airsearch.database.entity.Flight
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoritePresenterTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var presenter: FavoritePresenter
    private lateinit var view: FavoriteContract.View
    private lateinit var database: ProjectDatabase
    private lateinit var dao: FlightDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProjectDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.flightDao()
        presenter = FavoritePresenter(dao)
        view = mock(FavoriteContract.View::class.java)
        presenter.attachView(view)
    }

    @After
    fun teardown() {
        presenter.detachView()
        database.close()
    }

    @Test
    fun shouldInitRecyclerViewAndSetContent() = runTest {
        presenter.downloadDataFromDB()
        Mockito.verify(view).apply {
            initRecyclerView()
            setContent(Mockito.anyList())
        }
    }

    @Test
    fun testReturnAll() = runTest {
        val allFlights = generateTestFlights(Random.nextInt(1, 20))
        for (flight in allFlights) {
            dao.insert(flight)
        }
        val returnedFlights = dao.getAll()
        assertThat(returnedFlights).isEqualTo(allFlights)
    }

    private fun generateTestFlights(length: Int): List<Flight> {
        val flight = mutableListOf<Flight>()
        for (i in 1..length) {
            flight.add(
                Flight(
                    i.toString(),
                    "company",
                    "depCity",
                    "depAir",
                    "depTime",
                    "arrCity",
                    "arrAir",
                    "arrTime"
                )
            )
        }
        return flight.toList()
    }
}
