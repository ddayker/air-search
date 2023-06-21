package com.dayker.airsearch.ui.search

import android.util.Log
import com.dayker.airsearch.network.FlightsApiService
import com.dayker.airsearch.network.GeocodeService
import com.dayker.airsearch.utils.Constants
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class SearchPresenter(
    private val flightsApiService: FlightsApiService,
    private val geocodeService: GeocodeService
) : SearchContract.Presenter() {
    override fun findFlight(icao: String) {
        coroutineScope.launch {
            try {
                val response =
                    flightsApiService.getFlightInfo(
                        icao,
                        Constants.API_KEY
                    )
                withContext(Dispatchers.Main) {
                    view?.displayFlightOnMap(response.response)
                    SearchFragment.searchIcao = icao
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.showFlightNotFound()
                    SearchFragment.searchIcao = ""
                }
            }
        }
    }

    override fun getRouteCoordinatesAsync(departureCity: String, arrivalCity: String) =
        coroutineScope.async {
            try {
                val departureCityCoordinates =
                    geocodeService.getLocation(
                        departureCity,
                        Constants.MAPS_API_KEY
                    )
                val arrivalCityCoordinates =
                    geocodeService.getLocation(
                        arrivalCity,
                        Constants.MAPS_API_KEY
                    )
                withContext(Dispatchers.Main) {
                    if (departureCityCoordinates.location.isNotEmpty() && arrivalCityCoordinates.location.isNotEmpty()) {
                        view?.addRouteToMap(
                            departureCity = departureCityCoordinates.location[0].geolocation.coordinates,
                            arrivalCity = arrivalCityCoordinates.location[0].geolocation.coordinates
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("GEOCODE", "Geocoding error")
                println(e.message)
            }
        }

    /**
     * Calculates the azimuth from a given plane location to a target location.
     * Used to calculate the direction of the airplane marker on the map.
     *
     * @param plane The coordinates of the plane's location (latitude, longitude).
     * @param target The coordinates of the target location (latitude, longitude).
     * @return The azimuth in degrees, indicating the direction from the plane to the target.
     */
    override fun calculateAzimuth(plane: LatLng, target: LatLng): Double {
        // Convert the latitude and longitude values from degrees to radians
        val lat1 = Math.toRadians(plane.latitude)
        val lon1 = Math.toRadians(plane.longitude)
        val lat2 = Math.toRadians(target.latitude)
        val lon2 = Math.toRadians(target.longitude)
        // Calculate the difference in longitude
        val deltaLon = lon2 - lon1
        // Calculate the components of the directional vector
        val y = sin(deltaLon) * cos(lat2)
        val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(deltaLon)
        // Calculate the azimuth using the arctangent function and convert it to degrees
        var azimuth = Math.toDegrees(atan2(y, x))
        if (azimuth < 0) {
            azimuth += 360
        }
        return azimuth
    }
}