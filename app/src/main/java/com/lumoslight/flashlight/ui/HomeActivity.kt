package com.lumoslight.flashlight.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lumoslight.flashlight.R
import com.lumoslight.flashlight.config.Constants
import com.lumoslight.flashlight.databinding.ActivityHomeBinding
import com.lumoslight.flashlight.extentsion.inAppUpdate
import com.lumoslight.flashlight.extentsion.makeFullScreen
import com.lumoslight.flashlight.extentsion.themeMode

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        themeMode()
        super.onCreate(savedInstanceState)
        makeFullScreen()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAppUpdater()
        initializeViews()
        menuListener()
    }

    private fun initializeViews() {
        binding.apply {
            val drawerLayout: DrawerLayout = drawerLayout
            val navView: BottomNavigationView = mainContent.bottomNavView
            navController = findNavController(R.id.nav_host_fragment_activity_main)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                    R.id.settingFragment,
                    R.id.dummyMenu,
                ), drawerLayout
            )
            navView.setupWithNavController(navController)
            navView.setupWithNavController(navController)

            mainContent.btnDisplayColor.setOnClickListener() {
                startDisplayColorActivity()
            }

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                // Drawer is open
                mainContent.ivMenu.setBackgroundResource(R.color.backgroundColor)
                mainContent.ivMenu.setImageResource(R.drawable.ic_back)
                mainContent.ivMenu.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.START) }
            } else {
                // Drawer is closed
                mainContent.ivMenu.setBackgroundResource(R.drawable.ic_menu_border)
                mainContent.ivMenu.setImageResource(R.drawable.ic_menu)
                mainContent.ivMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
            }
        }
    }

    private fun startDisplayColorActivity() {
        val intent = Intent(this@HomeActivity, DisplayColorActivity::class.java)
        startActivity(intent)
    }

    private fun menuListener(){
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.privacyPolicy -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    startWeb(getString(R.string.privacy_policy_url))
                    true
                }
                R.id.aboutUs -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    startWeb(getString(R.string.about_us_url))
                    true
                }
                R.id.closeApp -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    onBackPressed()
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
       navController.navigate(R.id.exitDialogFragment)
    }

    private fun startWeb(url: String) {
        val intent = Intent(this@HomeActivity, WebActivity::class.java)
        intent.putExtra(Constants.URL, url)
        startActivity(intent)
    }

    private fun initAppUpdater() {
        inAppUpdate(this)
    }
}