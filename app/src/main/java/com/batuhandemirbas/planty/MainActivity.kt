package com.batuhandemirbas.planty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.batuhandemirbas.planty.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNavBar = binding.bottomNavigationView

        bottomNavBar.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                navController.currentDestination?.let { navDestination ->

                    val isOnOtherTabs = navDestination.id in setOf(
                        R.id.statisticFragment,
                        R.id.notificationFragment,
                        R.id.settingFragment
                    )

                    if (R.id.homeFragment == navDestination.id) {
                        finish()
                    } else if (isOnOtherTabs) {
                        bottomNavBar.selectedItemId = R.id.home
                    } else {
                        navController.popBackStack()
                    }

                }


            }

        })

        bottomNavBar.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.home -> {
                    navController.navigate(R.id.action_global_homeFragment)
                    true
                }

                R.id.statistic -> {
                    navController.navigate(R.id.action_global_statisticFragment)
                    it.setIcon(resources.getDrawable(R.drawable.statistic_filled))
                    true
                }

                R.id.notification -> {
                    navController.navigate(R.id.action_global_notificationFragment)
                    true
                }

                R.id.settings -> {
                    navController.navigate(R.id.action_global_settingFragment)
                    it.icon = resources.getDrawable(R.drawable.settings_filled)
                    true
                }

                else -> {

                    true
                }

            }

        }

    }


}