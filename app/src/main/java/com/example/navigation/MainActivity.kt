package com.example.navigation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.SparseArray
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.navigation.fragments.FirstScreenFragment
import com.example.navigation.fragments.SecondScreenFragment
import com.example.navigation.fragments.ThirdScreenFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val PERSIST_BOTTOM_NAVIGATION_STATE = "YO"
    }

    private var currentNavController: LiveData<NavController>? = null
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var stackGlobal: ArrayList<Int>
    private var curBackStack = 0
    private val graphIdToTagMap = SparseArray<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stackGlobal =
            savedInstanceState?.getIntegerArrayList(PERSIST_BOTTOM_NAVIGATION_STATE)
                ?: ArrayList<Int>().also { it -> it.add(R.id.first_screen) }
        setupBottomNavigation()
        savedInstanceState?.let {
            bottomNavigationView.selectedItemId = it.getInt(PERSIST_BOTTOM_NAVIGATION_STATE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList(PERSIST_BOTTOM_NAVIGATION_STATE, stackGlobal)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (getCurCount(curBackStack) != 0 || stackGlobal.size == 1) {
            super.onBackPressed()
        } else {
            curBackStack = bottomNavigationView.handleBackButtonPressed(
                supportFragmentManager,
                stackGlobal,
                graphIdToTagMap
            )
        }
    }

    private fun getCurCount(num: Int): Int {
        return when (num) {
            0 -> FirstScreenFragment.globalCount
            1 -> SecondScreenFragment.globalCount
            else -> ThirdScreenFragment.globalCount
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        val navGraphIds = listOf(
            R.navigation.first_screen_navigation,
            R.navigation.second_screen_navigation,
            R.navigation.third_screen_navigation
        )
        bottomNavigationView.selectedItemId = stackGlobal.last()
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.my_nav_host_fragment,
            intent = intent,
            stackGlobal = stackGlobal,
            graphIdToTagMap = graphIdToTagMap
        ) { newGraph -> curBackStack = newGraph }
        currentNavController = controller
    }
}