package com.dayker.airsearch.ui.main.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.FragmentSettingsBinding
import com.dayker.airsearch.model.country.Country
import com.dayker.airsearch.utils.Constants
import com.dayker.airsearch.utils.Utils.getDeviceRegion
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment(), SettingsContract.View {

    private var binding: FragmentSettingsBinding? = null
    private val presenter: SettingsContract.Presenter by inject()
    private val sharedPreferences: SharedPreferences by inject()
    private var adapter: CountriesAdaptor? = null
    private var countries = listOf<Country>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val region = if (sharedPreferences.contains(Constants.REGION_KEY)) {
            sharedPreferences.getString(Constants.REGION_KEY, "").toString()
        } else {
            getDeviceRegion()
        }
        binding?.tvRegion?.text = region
        binding?.btnBack?.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_mainFragment)
        }
        presenter.attachView(this)
        presenter.downloadCountriesFromApi()
        binding?.searchView?.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterCountriesByName(newText)
                return true
            }
        })
        binding?.btnReset?.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            val defRegion = getDeviceRegion()
            val message = getString(R.string.region_changed)
            Snackbar.make(binding!!.root, "$message $defRegion", Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_settingsFragment_to_mainFragment)
        }
    }

    override fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rv?.layoutManager = layoutManager
    }

    override fun setCountries(countries: List<Country>) {
        if (this.countries.isEmpty()) {
            this.countries = countries
        }
        if (adapter == null) {
            adapter = CountriesAdaptor(countries, sharedPreferences)
            binding?.rv?.adapter = adapter
        } else {
            adapter?.updateData(countries)
        }
    }

    override fun dataIsNotAvailable() {
        binding?.searchLayout?.visibility = View.GONE
    }

    fun filterCountriesByName(query: String) {
        var filteredList = countries.toList()
        filteredList = if (query.isNotEmpty()) {
            filteredList.filter { country ->
                country.name.lowercase().contains(query.lowercase())
            }
        } else {
            filteredList
        }
        adapter?.updateData(filteredList)
    }


}