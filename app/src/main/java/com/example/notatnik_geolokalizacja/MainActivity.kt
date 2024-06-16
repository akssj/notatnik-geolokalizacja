package com.example.notatnik_geolokalizacja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 * Entry point of the application.
 * Sets up the navigation controller to manage navigation between fragments.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activityMainView) as NavHostFragment
        navController = navHostFragment.navController
    }
}