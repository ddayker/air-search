package com.dayker.airsearch.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.FragmentMainBinding
import com.dayker.airsearch.model.flight.ActualFlight
import com.dayker.airsearch.utils.Constants.FIREBASE_MESSAGE_KEY
import com.dayker.airsearch.utils.Constants.FLIGHTS_LIMIT
import com.dayker.airsearch.utils.Constants.REGION_KEY
import com.dayker.airsearch.utils.Utils.getDeviceRegion
import com.dayker.airsearch.utils.Utils.isConnectionError
import org.koin.android.ext.android.inject

class MainFragment : Fragment(), MainContract.View {

    private var binding: FragmentMainBinding? = null
    private val presenter: MainContract.Presenter by inject()
    private val sharedPreferences: SharedPreferences by inject()
    private var adapter: MainAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDownloadBar()
        val region = if (sharedPreferences.contains(REGION_KEY)) {
            sharedPreferences.getString(REGION_KEY, "").toString()
        } else {
            getDeviceRegion()
        }
        if (isConnectionError(requireContext())) {
            showConnectionError()
        } else {
            binding?.CLNoConnection?.visibility = View.GONE
            presenter.attachView(this)
            presenter.downloadDataFromApi(region)
        }
        binding?.tvMain?.setOnClickListener {
            presenter.getRemoteMessage(FIREBASE_MESSAGE_KEY)
        }
        binding?.btnReconnect?.setOnClickListener {
            refreshFragment()
        }
        binding?.regionRadio?.setOnClickListener {
            presenter.downloadDataFromApi(region)
            binding?.btnSettings?.visibility = View.VISIBLE
            showDownloadBar()
        }

        binding?.allRadio?.setOnClickListener {
            presenter.downloadDataFromApi()
            binding?.btnSettings?.visibility = View.GONE
            showDownloadBar()
        }
        binding?.btnSettings?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }
    }

    override fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rv?.layoutManager = layoutManager
        adapter = MainAdapter(emptyList())
        binding?.rv?.adapter = adapter
    }

    override fun setContent(flights: List<ActualFlight>) {
        if (flights.isEmpty()) {
            showFlightsNotFound()
        } else {
            hideNotFoundMessage()
        }
        if (adapter == null) {
            adapter = MainAdapter(flights.take(FLIGHTS_LIMIT))
            binding?.rv?.adapter = adapter
        } else {
            adapter?.updateData(flights.take(FLIGHTS_LIMIT))
        }
        binding?.rv?.visibility = View.VISIBLE
        binding?.progressAnimation?.visibility = View.GONE
    }

    override fun setRemoteText(text: String) {
        binding?.tvMain?.text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    private fun showConnectionError() {
        val reconnectionDelay = 2000L
        binding?.progressAnimation?.visibility = View.GONE
        binding?.btnSettings?.visibility = View.GONE
        binding?.rv?.visibility = View.GONE
        binding?.tvNoConnection?.visibility = View.VISIBLE
        binding?.ivNoConnection?.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            binding?.btnReconnect?.visibility = View.VISIBLE
        }, reconnectionDelay)
    }

    private fun refreshFragment() {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val currentFragment = fragmentManager.findFragmentById(R.id.flContainer)
        val newInstance = currentFragment?.javaClass?.newInstance()
        newInstance?.arguments = currentFragment?.arguments
        fragmentTransaction.replace(R.id.flContainer, newInstance!!)
        fragmentTransaction.commit()
    }

    private fun showFlightsNotFound() {
        binding?.progressAnimation?.visibility = View.GONE
        binding?.CLNotFound?.visibility = View.VISIBLE
        binding?.ivNotFound?.visibility = View.VISIBLE
        binding?.tvNotFound?.visibility = View.VISIBLE
    }

    private fun hideNotFoundMessage() {
        binding?.progressAnimation?.visibility = View.GONE
        binding?.CLNotFound?.visibility = View.GONE
        binding?.ivNotFound?.visibility = View.GONE
        binding?.tvNotFound?.visibility = View.GONE
    }

    private fun showDownloadBar() {
        binding?.rv?.visibility = View.INVISIBLE
        binding?.progressAnimation?.visibility = View.VISIBLE
    }


}

