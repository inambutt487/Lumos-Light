package com.lumoslight.flashlight.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lumoslight.flashlight.config.Constants
import com.lumoslight.flashlight.databinding.ActivityWebBinding
import com.lumoslight.flashlight.extentsion.makeFullScreen
import com.lumoslight.flashlight.extentsion.themeMode

class WebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        themeMode()
        super.onCreate(savedInstanceState)
        makeFullScreen()
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(Constants.URL)
        if (url != null) {
            // Do whatever you need with the URL
            loadUrl(url)
        }
    }

    private fun loadUrl(url: String) {
        binding.web.loadUrl(url)
    }
}