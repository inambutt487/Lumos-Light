package com.lumoslight.flashlight.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.lumoslight.flashlight.R
import com.lumoslight.flashlight.config.Constants
import com.lumoslight.flashlight.databinding.ActivitySplashBinding
import com.lumoslight.flashlight.extentsion.makeFullScreen
import com.lumoslight.flashlight.extentsion.setTapsoundOnClick
import com.lumoslight.flashlight.extentsion.themeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StartScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        themeMode()
        super.onCreate(savedInstanceState)
        makeFullScreen()
        installSplashScreen()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()
    }

    private fun listener() {
        binding.btnLetsGetStarted.setTapsoundOnClick(this, R.raw.tapsounds)
        binding.tvPrivacyPolicy.setTapsoundOnClick(this, R.raw.tapsounds)
        binding.btnLetsGetStarted.setOnClickListener {
            lifecycleScope.launch {
                startHome()
            }
        }
        binding.tvPrivacyPolicy.setOnClickListener {
            startWeb(getString(R.string.privacy_policy_url))
        }
    }

    private suspend fun startHome() {
        withContext(Dispatchers.Main) {
            val intent = Intent(this@StartScreenActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startWeb(url: String) {
        val intent = Intent(this@StartScreenActivity, WebActivity::class.java)
        intent.putExtra(Constants.URL, url)
        startActivity(intent)
    }
}