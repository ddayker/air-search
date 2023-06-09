package com.dayker.airsearch.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.FragmentMainBinding
import com.dayker.airsearch.model.ActualFlight
import com.dayker.airsearch.utils.ApiUtils.isConnectionError
import com.dayker.airsearch.utils.Constants.FIREBASE_MESSAGE_KEY
import org.koin.android.ext.android.inject

class MainFragment : Fragment(), MainContract.View {

    private var binding: FragmentMainBinding? = null
    private val presenter: MainContract.Presenter by inject()
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
        if (isConnectionError(requireContext())) {
            showConnectionError()
        } else {
            binding?.CLNoConnection?.visibility = View.GONE
            presenter.attachView(this)
            presenter.downloadDataFromApi()
        }
        binding?.tvMain?.setOnClickListener {
            presenter.getRemoteMessage(FIREBASE_MESSAGE_KEY)
        }
        binding?.btnReconnect?.setOnClickListener {
            refreshFragment()
        }
    }

    override fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rv?.layoutManager = layoutManager
    }

    override fun setContent(flights: List<ActualFlight>) {
        if (adapter == null) {
            adapter = MainAdapter(flights)
            binding?.rv?.adapter = adapter
        } else {
            adapter?.updateData(flights)
        }
    }

    override fun setRemoteText(text: String) {
        binding?.tvMain?.text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    private fun showConnectionError() {
        binding?.rv?.visibility = View.GONE
        binding?.tvNoConnection?.visibility = View.VISIBLE
        binding?.ivNoConnection?.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            binding?.btnReconnect?.visibility = View.VISIBLE
        }, 2000)
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

}

