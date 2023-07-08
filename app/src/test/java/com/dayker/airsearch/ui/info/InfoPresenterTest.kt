package com.dayker.airsearch.ui.info

import com.dayker.airsearch.database.dao.FlightDao
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.network.FlightsApiService
import com.dayker.airsearch.utils.Constants.API_KEY
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


@ExperimentalCoroutinesApi
class InfoPresenterTest {

    private lateinit var presenter: InfoPresenter
    private lateinit var view: InfoContract.View
    private lateinit var dao: FlightDao
    private lateinit var flightsApiService: FlightsApiService


    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        view = mock()
        flightsApiService = mock()
        dao = mock()
        presenter = InfoPresenter(flightsApiService, dao)
        presenter.attachView(view)
    }

    @After
    fun teardown() {
        presenter.detachView()
        Dispatchers.resetMain()
    }


    @Test
    fun `should getFlight when flight already saved in Favorites`() = runTest {
        val icao = "TEST_ICAO"
        val flight = mock(Flight::class.java)
        `when`(dao.getFlight(icao)).thenReturn(flight)
        val result = presenter.checkForFavoriteAndDownload(icao)
        assertThat(result).isTrue() //flight already in favorites
        verify(dao).getFlight(icao)
        verify(view).setContent(flight)
        verify(view, never()).dataIsNotAvailable()
        verify(flightsApiService, never()).getFlightInfo(anyString(), anyString())
    }

    @Test
    fun `should getFlightInfo from API when flight not in favorites`() = runTest {
        val icao = "TEST_ICAO"
        `when`(dao.getFlight(icao)).thenReturn(null)
        val result = presenter.checkForFavoriteAndDownload(icao)
        delay(100)
        assertThat(result).isFalse() //flight not in favorites
        verify(flightsApiService).getFlightInfo(icao, API_KEY)
    }

}