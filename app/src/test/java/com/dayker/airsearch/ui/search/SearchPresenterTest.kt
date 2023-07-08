package com.dayker.airsearch.ui.search

import com.dayker.airsearch.model.info.FlightInfo
import com.dayker.airsearch.model.info.FlightInfoResponse
import com.dayker.airsearch.model.location.Geolocation
import com.dayker.airsearch.model.location.GeolocationResponse
import com.dayker.airsearch.model.location.Place
import com.dayker.airsearch.network.FlightsApiService
import com.dayker.airsearch.network.GeocodeService
import com.dayker.airsearch.utils.Constants
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify


@ExperimentalCoroutinesApi
class SearchPresenterTest {

    private lateinit var presenter: SearchPresenter
    private lateinit var view: SearchContract.View
    private lateinit var flightsApiService: FlightsApiService
    private lateinit var geocodeService: GeocodeService


    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        flightsApiService = mock()
        geocodeService = mock()
        presenter = SearchPresenter(flightsApiService, geocodeService)
        view = mock()
        presenter.attachView(view)
    }

    @After
    fun teardown() {
        presenter.detachView()
        Dispatchers.resetMain()
    }

    @Test
    fun `should call displayFlightOnMap if response is successful`() = runBlocking {
        val icao = "TEST_ICAO"
        val response = FlightInfoResponse(mock(FlightInfo::class.java))
        `when`(flightsApiService.getFlightInfo(icao, Constants.API_KEY)).thenReturn(response)
        presenter.findFlight(icao)
        delay(100)
        verify(view).displayFlightOnMap(response.response)
    }

    @Test
    fun `should call showFlightNotFound`() = runTest {
        val icao = "TEST_ICAO"
        `when`(
            flightsApiService.getFlightInfo(
                icao,
                Constants.API_KEY
            )
        ).thenThrow(RuntimeException())
        presenter.findFlight(icao)
        delay(100)
        verify(view).showFlightNotFound()
    }

    @Test
    fun `should call addRouteToMap when both departure and arrival city coordinates are available`() =
        runTest {
            val departureCity = "City A"
            val arrivalCity = "City B"
            val departureLocation = Place(mock(Geolocation::class.java), "")
            val arrivalLocation = Place(mock(Geolocation::class.java), "")
            val departureCityCoordinates = GeolocationResponse(listOf(departureLocation), "")
            val arrivalCityCoordinates = GeolocationResponse(listOf(arrivalLocation), "")
            `when`(geocodeService.getLocation(departureCity, Constants.MAPS_API_KEY)).thenReturn(
                departureCityCoordinates
            )
            `when`(geocodeService.getLocation(arrivalCity, Constants.MAPS_API_KEY)).thenReturn(
                arrivalCityCoordinates
            )
            presenter.getRouteCoordinatesAsync(departureCity, arrivalCity).await()
            verify(view).addRouteToMap(
                departureCityCoordinates.location[0].geolocation.coordinates,
                arrivalCityCoordinates.location[0].geolocation.coordinates
            )
        }

    @Test
    fun `azimuth should be 0 for same location`() {
        val plane = LatLng(10.0, 20.0)
        val target = LatLng(10.0, 20.0)
        val expectedAzimuth = 0.0
        val actualAzimuth = presenter.calculateAzimuth(plane, target)
        assertThat(actualAzimuth).isEqualTo(expectedAzimuth)
    }


    @Test
    fun `azimuth should be 180 for same latitude`() {
        val plane = LatLng(30.0, 20.0)
        val target = LatLng(10.0, 20.0)
        val expectedAzimuth = 180.0
        val actualAzimuth = presenter.calculateAzimuth(plane, target)
        assertThat(actualAzimuth).isEqualTo(expectedAzimuth)
    }
}

