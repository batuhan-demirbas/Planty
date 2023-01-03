package com.batuhandemirbas.planty

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.batuhandemirbas.planty.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding.buttonWatering) {
            setMaxImageSize(120)
            imageTintList = ResourcesCompat.getColorStateList(resources, R.color.white, null)

        }
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
                        R.id.statisticsFragment,
                        R.id.notificationFragment,
                        R.id.settingsFragment
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
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.action_global_homeFragment)
                        it.icon =
                            ResourcesCompat.getDrawable(resources, R.drawable.ic_home_filled, null)
                    }
                    true
                }

                R.id.statistics -> {
                    if (navController.currentDestination?.id != R.id.statisticsFragment) {
                        navController.navigate(R.id.action_global_statisticsFragment)
                        it.icon = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_statistic_filled,
                            null
                        )
                    }

                    true
                }

                R.id.notification -> {
                    if (navController.currentDestination?.id != R.id.action_global_notificationFragment) {
                        navController.navigate(R.id.action_global_notificationFragment)
                        it.icon =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_notification_filled,
                                null
                            )
                    }
                    true
                }

                R.id.settings -> {
                    if (navController.currentDestination?.id != R.id.settingsFragment) {
                        navController.navigate(R.id.settingsFragment)
                        it.icon = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_settings_filled,
                            null
                        )
                    }

                    true
                }

                else -> {

                    true
                }

            }

        }

    }


}