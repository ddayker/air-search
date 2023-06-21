package com.dayker.airsearch.ui.search

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.model.info.FlightInfo
import com.dayker.airsearch.model.location.Coordinates
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Deferred

interface SearchContract {

    interface View : BaseView {
        fun showFlightNotFound()
        suspend fun displayFlightOnMap(flight: FlightInfo)
        fun addRouteToMap(departureCity: Coordinates, arrivalCity: Coordinates)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun findFlight(icao: String)
        abstract fun getRouteCoordinatesAsync(
            departureCity: String,
            arrivalCity: String
        ): Deferred<Unit?>

        abstract fun calculateAzimuth(plane: LatLng, target: LatLng): Double
    }
}