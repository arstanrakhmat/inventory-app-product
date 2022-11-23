package com.example.inventoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.inventoryapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavView = binding.bottomNavigationBtn
        navController = findNavController(R.id.navigation_host)
        bottomNavView.setupWithNavController(navController)

        setUpNav()
    }

    private fun setUpNav() {
        val navCtrl = findNavController(R.id.navigation_host)
        findViewById<BottomNavigationView>(R.id.bottomNavigationBtn)
            .setupWithNavController(navCtrl)

        navCtrl.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addProductFragment -> hideBottomNav()
                R.id.updateFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }

    private fun hideBottomNav() {
        bottomNavView.visibility = View.GONE
    }

    private fun showBottomNav() {
        bottomNavView.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}