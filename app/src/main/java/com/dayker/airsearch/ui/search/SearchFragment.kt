package com.dayker.airsearch.ui.search

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.FragmentSearchBinding
import com.dayker.airsearch.model.info.FlightInfo
import com.dayker.airsearch.model.location.Coordinates
import com.dayker.airsearch.ui.info.InfoActivity
import com.dayker.airsearch.utils.Constants
import com.dayker.airsearch.utils.Constants.IMAGE_ANGLE
import com.dayker.airsearch.utils.Utils.bitmapDescriptorFromVector
import com.dayker.airsearch.utils.Utils.isConnectionError
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.koin.android.ext.android.inject


class SearchFragment : Fragment(), SearchContract.View {
    companion object {
        var searchIcao: String = ""
    }

    private var binding: FragmentSearchBinding? = null
    private val presenter: SearchContract.Presenter by inject()
    private lateinit var googleMap: GoogleMap
    private lateinit var planePosition: LatLng
    private lateinit var arrivalCoordinates: LatLng
    private var planeMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        initMap(mapFragment)

        if (searchIcao.isNotEmpty()) {
            binding?.editTextSearch?.setText(searchIcao)
            searchFlight(mapFragment, searchIcao)
        }
        binding?.btnSearch?.setOnClickListener {
            searchIcao = binding?.editTextSearch?.text.toString()
            if (searchIcao.isNotEmpty()) {
                if (isConnectionError(requireContext())) {
                    showConnectionError()
                } else {
                    searchFlight(mapFragment, searchIcao)
                }
            }
        }
        binding?.btnMessageClose?.setOnClickListener {
            binding?.notFoundMessageLayout?.visibility = View.GONE
        }
    }

    private fun initMap(mapFragment: SupportMapFragment?) {
        mapFragment?.getMapAsync { map ->
            googleMap = map
            setupMap()
        }
    }

    private fun searchFlight(mapFragment: SupportMapFragment?, icao: String) {
        presenter.findFlight(icao)
        mapFragment?.getMapAsync { map ->
            googleMap = map
            setupMap()
        }
    }

    override suspend fun displayFlightOnMap(flight: FlightInfo) {
        binding?.notFoundMessageLayout?.visibility = View.GONE
        val latitude = flight.lat
        val longitude = flight.lng
        val rout = "${flight.depCity} - ${flight.arrCity}"
        searchIcao = flight.flightIcao
        planePosition = LatLng(latitude, longitude)
        presenter.getRouteCoordinatesAsync(flight.depCity, flight.arrCity).await()
        addPlaneMarkerToMap(rout, searchIcao)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(planePosition, 5F))
    }

    /**
     * Adds a marker representing a plane to the map.
     * In order for the airplane to look towards the target, the angle of rotation is calculated
     * using the azimuth calculation function and the initial angle of rotation of the airplane marker.
     *
     * @param title The title of the marker.
     * @param snippet The snippet (additional information) of the marker.
     */
    private fun addPlaneMarkerToMap(title: String, snippet: String) {
        val angle = presenter.calculateAzimuth(planePosition, arrivalCoordinates)
        val markerOptions = MarkerOptions().position(planePosition).title(title).snippet(snippet)
            .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.plane_marker))
            .rotation(angle.toFloat() - IMAGE_ANGLE)
        planeMarker = googleMap.addMarker(markerOptions)
    }

    override fun addRouteToMap(departureCity: Coordinates, arrivalCity: Coordinates) {
        val departureCoordinates = LatLng(departureCity.lat, departureCity.lng)
        arrivalCoordinates = LatLng(arrivalCity.lat, arrivalCity.lng)
        val departurePolylineOptions = PolylineOptions()
            .add(departureCoordinates).add(planePosition).add(arrivalCoordinates)
            .color(R.color.blue3).width(15f).geodesic(true)
        googleMap.addPolyline(departurePolylineOptions)
        val departureMarker = MarkerOptions().position(departureCoordinates)
            .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.map_marker_radius))
            .alpha(0.7f)
        val arrivalMarker = MarkerOptions().position(arrivalCoordinates)
            .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.map_flag_marker))
            .alpha(0.7f)
        googleMap.addMarker(departureMarker)
        googleMap.addMarker(arrivalMarker)
    }

    private fun setupMap() {
        try {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.map_style
                )
            )
        } catch (e: Resources.NotFoundException) {
            println(e.message)
        }
        val markerInfoWindowAdapter = MapWindowAdapter(requireContext())
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter)
        googleMap.setOnMarkerClickListener { marker ->
            if (marker == planeMarker) {
                marker.showInfoWindow()
                true
            } else {
                true
            }
        }
        googleMap.setOnInfoWindowClickListener {
            val intent = Intent(requireContext(), InfoActivity::class.java)
            intent.putExtra(Constants.ICAO_KEY, searchIcao)
            startActivity(intent)
        }
    }

    override fun showFlightNotFound() {
        binding?.notFoundMessage?.text = getString(R.string.is_not_detected)
        binding?.notFoundMessageLayout?.visibility = View.VISIBLE
        if (planeMarker != null) {
            googleMap.clear()
        }

    }

    private fun showConnectionError() {
        binding?.notFoundMessage?.text = getString(R.string.no_internet_connection)
        binding?.notFoundMessageLayout?.visibility = View.VISIBLE
        if (planeMarker != null) {
            googleMap.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}






