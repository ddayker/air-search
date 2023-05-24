package com.dayker.airsearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.FragmentMainBinding
import com.dayker.airsearch.model.Response
import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.ApiUtils.retrofitInit
import com.dayker.airsearch.utils.Constants.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class MainFragment : Fragment(), MainContract.View {

    private var binding: FragmentMainBinding? = null
    private val presenter: MainContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.downloadDataFromApi()
    }

     override fun initRecyclerView() {
         val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
         binding?.rv?.layoutManager = layoutManager
     }

     override fun setContent(flights: List<Response>) {
        val adapter = MainAdapter(flights)
        binding?.rv?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

}