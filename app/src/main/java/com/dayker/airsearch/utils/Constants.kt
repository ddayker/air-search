package com.dayker.airsearch.utils

import com.dayker.airsearch.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val MAPS_API_KEY = BuildConfig.MAPS_API_KEY
    const val GEOCODE_BASE_URL = "https://maps.googleapis.com/maps/api/"
    const val BASE_URL = "https://airlabs.co/api/v9/"
    const val SHARED_PREFERENCES = "shared_preferences"
    const val ICAO_KEY = "icao"
    const val REGION_KEY = "region"
    const val FIREBASE_MESSAGE_KEY = "app_message"
    const val SHOW_ON_MAP_KEY = "show_map"
    const val WITHOUT_REGION_KEY = "without_region"

    /**
     * Configuration animation of the zoom
     */
    const val ZOOM_LEVEL = 8F
    const val ZOOM_DURATION = 2500

    /**
     * The number limiting the number of flights on the main page.
     */
    const val FLIGHTS_LIMIT = 1000

    /**
     * The angle (in degrees) by which the plane marker's rotation is adjusted.
     * This constant value is used to fine-tune the rotation of the marker on the map
     * for a custom aircraft marker from resources. (drawable/plane_marker.xml)
     */
    const val IMAGE_ANGLE = 40
}