package com.dayker.airsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.ActivityMainBinding
import com.dayker.airsearch.ui.favorite.FavoriteFragment
import com.dayker.airsearch.ui.main.MainFragment
import com.dayker.airsearch.ui.search.SearchFragment
import com.dayker.airsearch.utils.Constants.EVENT_FAVORITE
import com.dayker.airsearch.utils.Constants.EVENT_MAIN
import com.dayker.airsearch.utils.Constants.EVENT_SEARCH
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.selectedItemId = R.id.fragmentMain
        setFragment(MainFragment())
        analytics = Firebase.analytics
        val bundle = Bundle()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentMain -> {
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, EVENT_MAIN)
                    analytics.logEvent(EVENT_MAIN, bundle)
                    setFragment(MainFragment())
                }
                R.id.fragmentSearch -> {
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, EVENT_SEARCH)
                    analytics.logEvent(EVENT_SEARCH, bundle)
                    setFragment(SearchFragment())
                }
                R.id.fragmentFavorite -> {
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, EVENT_FAVORITE)
                    analytics.logEvent(EVENT_FAVORITE, bundle)
                    setFragment(FavoriteFragment())
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        val container = R.id.flContainer
        supportFragmentManager
            .beginTransaction()
            .replace(container, fragment)
            .commit()
    }
}
