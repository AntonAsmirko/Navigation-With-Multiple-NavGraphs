package com.example.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        const val PERSIST_BOTTOM_NAVIGATION_STATE = "YO"
    }

    private var currentNavController: LiveData<NavController>? = null
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation()
        savedInstanceState?.let {
            bottomNavigationView.selectedItemId = it.getInt(PERSIST_BOTTOM_NAVIGATION_STATE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PERSIST_BOTTOM_NAVIGATION_STATE, bottomNavigationView.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    private fun setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        val navGraphIds = listOf(
            R.navigation.first_screen_navigation,
            R.navigation.second_screen_navigation,
            R.navigation.third_screen_navigation
        )
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.my_nav_host_fragment,
            intent = intent
        )
        currentNavController = controller
    }
}