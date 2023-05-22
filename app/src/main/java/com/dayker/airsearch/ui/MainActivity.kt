package com.dayker.airsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.ActivityMainBinding
import com.dayker.airsearch.ui.favorite.FavoriteFragment
import com.dayker.airsearch.ui.main.MainFragment
import com.dayker.airsearch.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set the initial screen
        binding.bottomNavigationView.selectedItemId = R.id.fragmentMain
        setFragment(MainFragment())
        // set the screen depending on the bottom menu
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentMain -> {
                    setFragment(MainFragment())
                }
                R.id.fragmentSearch -> {
                    setFragment(SearchFragment())
                }
                R.id.fragmentFavorite -> {
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
