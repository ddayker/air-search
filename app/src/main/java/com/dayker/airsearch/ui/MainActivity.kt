package com.dayker.airsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.ActivityMainBinding
import com.dayker.airsearch.utils.Constants.SHOW_ON_MAP_KEY

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.flContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val showMap = intent.getBooleanExtra(SHOW_ON_MAP_KEY, false)
        if (showMap) {
            binding.bottomNavigationView.post {
                binding.bottomNavigationView.selectedItemId = R.id.searchFragment
                navController.navigate(R.id.searchFragment)
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
