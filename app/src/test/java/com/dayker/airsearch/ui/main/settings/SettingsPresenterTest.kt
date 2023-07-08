package com.dayker.airsearch.ui.main.settings

import com.dayker.airsearch.model.country.CountriesResponse
import com.dayker.airsearch.model.country.Country
import com.dayker.airsearch.network.FlightsApiService
import com.dayker.airsearch.utils.Constants.API_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class SettingsPresenterTest {

    private lateinit var presenter: SettingsPresenter
    private lateinit var view: SettingsContract.View
    private lateinit var flightsApiService: FlightsApiService

    @Before
    fun setup() {
        flightsApiService = mock()
        presenter = SettingsPresenter(flightsApiService)
        view = mock()
        presenter.attachView(view)
    }

    @After
    fun teardown() {
        presenter.detachView()
    }

    @Test
    fun `should successfully receive a response and call setCountries`() = runTest {
        val mockCountries = listOf(
            Mockito.mock(Country::class.java),
            Mockito.mock(Country::class.java),
            Mockito.mock(Country::class.java)
        )
        val response = CountriesResponse(mockCountries)
        `when`(flightsApiService.getCountries(API_KEY)).thenReturn(response)
        presenter.downloadCountriesFromApi()
        Mockito.verify(view).apply {
            initRecyclerView()
            setCountries(response.countries)
        }
    }

    @Test
    fun `should not be called setCountries because of Exception`() = runTest {
        val mockCountries = listOf(
            Mockito.mock(Country::class.java),
            Mockito.mock(Country::class.java),
            Mockito.mock(Country::class.java)
        )
        val response = CountriesResponse(mockCountries)
        `when`(flightsApiService.getCountries(API_KEY)).thenThrow(java.lang.RuntimeException())
        presenter.downloadCountriesFromApi()
        Mockito.verify(view, Mockito.never()).setCountries(response.countries)
    }


}