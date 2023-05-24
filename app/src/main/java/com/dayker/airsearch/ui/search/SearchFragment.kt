package com.dayker.airsearch.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.FragmentSearchBinding
import com.dayker.airsearch.model.ResponseX
import com.dayker.airsearch.ui.info.InfoActivity
import com.dayker.airsearch.utils.Constants
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class SearchFragment : Fragment(), SearchContract.View {

    private var binding: FragmentSearchBinding? = null
    private val presenter: SearchContract.Presenter by inject()

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

        binding?.btnSearch?.setOnClickListener {
            val icaoCode = binding?.editTextSearch?.text.toString()
            presenter.findFlight(icao = icaoCode)
        }

        binding?.constraintLayoutInfo?.setOnClickListener {
            val intent = Intent(requireContext(), InfoActivity::class.java)
            intent.putExtra(Constants.ICAO_KEY, binding?.tvFlightNumber?.text)
            this.startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showFlightNotFound() {
        binding?.constraintLayoutInfo?.visibility = View.GONE
        binding?.notFoundMessage?.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    override fun showFlightInfo(flight: ResponseX) {
        binding?.notFoundMessage?.visibility = View.GONE
        binding?.constraintLayoutInfo?.visibility = View.VISIBLE
        binding?.tvCity1?.text = flight.depCity
        binding?.tvCity2?.text = flight.arrCity
        binding?.tvCompany?.text = flight.airlineName
        binding?.tvFlightNumber?.text = flight.flightIcao
        binding?.tvStatus?.text = flight.status
        binding?.tvDate?.text = "${flight.depTime} - ${flight.arrTime}"
    }

}