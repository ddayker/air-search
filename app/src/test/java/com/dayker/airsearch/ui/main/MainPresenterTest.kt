package com.dayker.airsearch.ui.main

import com.dayker.airsearch.model.flight.ActualFlight
import com.dayker.airsearch.model.flight.ActualFlightResponse
import com.dayker.airsearch.network.FlightsApiService
import com.dayker.airsearch.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
class MainPresenterTest {

    private lateinit var presenter: MainPresenter
    private lateinit var view: MainContract.View
    private lateinit var flightsApiService: FlightsApiService

    private fun createResponse(): ActualFlightResponse {
        val mockFlights = listOf(
            Mockito.mock(ActualFlight::class.java),
            Mockito.mock(ActualFlight::class.java),
            Mockito.mock(ActualFlight::class.java)
        )
        return ActualFlightResponse(mockFlights)
    }


    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        flightsApiService = mock()
        presenter = MainPresenter(flightsApiService)
        view = mock()
        presenter.attachView(view)
    }

    @After
    fun teardown() {
        presenter.detachView()
        Dispatchers.resetMain()
    }

    @Test
    fun `verify initRecyclerView() call`() {
        presenter.downloadDataFromApi()
        Mockito.verify(view).initRecyclerView()
    }


    @Test
    fun `with param should call getFlightsWithRegion`() = runTest {
        val region = "region"
        presenter.downloadDataFromApi(region)
        delay(100)
        Mockito.verify(flightsApiService).getFlightsWithRegion(API_KEY, region)
        Mockito.verify(flightsApiService, Mockito.never()).getFlights(API_KEY)
    }

    @Test
    fun `without param should call getFlights`() = runTest {
        presenter.downloadDataFromApi()
        delay(100)
        Mockito.verify(flightsApiService).getFlights(API_KEY)
    }

    @Test
    fun `should call setContent`() = runTest {
        val response = createResponse()
        `when`(flightsApiService.getFlights(API_KEY)).thenReturn(response)
        presenter.downloadDataFromApi()
        Mockito.verify(view).apply {
            initRecyclerView()
            setContent(response.response)
        }
    }

    @Test
    fun `if exception setContent should not be called`() = runTest {
        `when`(flightsApiService.getFlights(API_KEY)).thenThrow(
            java.lang.RuntimeException()
        )
        presenter.downloadDataFromApi()
        Mockito.verify(view).initRecyclerView()
        Mockito.verify(view, Mockito.never()).setContent(Mockito.anyList())
    }
}
