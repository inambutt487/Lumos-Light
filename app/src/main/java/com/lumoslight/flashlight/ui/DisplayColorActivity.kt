package com.lumoslight.flashlight.ui

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lumoslight.flashlight.databinding.ActivityDisplayColorBinding
import com.lumoslight.flashlight.extentsion.gone
import com.lumoslight.flashlight.extentsion.themeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random

class DisplayColorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayColorBinding

    private lateinit var countDownTimer: CountDownTimer

    private var brightnessBeforeChanged = 0f

    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        themeMode()
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayColorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSystemUIVisibility(true)
        init()
    }

    private fun init() {

        val params: WindowManager.LayoutParams = window.attributes

        // Save brightness to restore it later
        brightnessBeforeChanged = params.screenBrightness

        // Set screen brightness to maximum
        params.screenBrightness = 1f
        window.attributes = params


        counterTimer()
    }

    private fun startCountDownTimer() {
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 50) {
            override fun onTick(millisUntilFinished: Long) {
                lifecycleScope.launch {
                    startScreenColor()
                }
            }

            override fun onFinish() {
                // Do nothing here
            }
        }
        countDownTimer.start()
    }

    private suspend fun startScreenColor() {
        withContext(Dispatchers.Main) {
            // Change the background color to a random color
            val randomColor = getRandomColor()
            binding.screenLight.setBackgroundColor(randomColor)
            delay(50) // Pause for 1 second before changing the color again
        }
    }

    private fun getRandomColor(): Int {
        // Generate a random color
        return -0x1000000 or random.nextInt(0x1000000)
    }

    private fun counterTimer() {
        val countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Display remaining time in seconds on tvCountDown
                binding.tvCountDown.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                // When countdown finishes, call startScreenColor
                binding.tvCountDown.gone()
                startCountDownTimer()
            }
        }
        countDownTimer.start()
    }

    private fun setSystemUIVisibility(hide: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val window = window.insetsController
            val windows = WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
            if (hide) window?.hide(windows) else window?.show(windows)
            // needed for hide, doesn't do anything in show
            window?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            val view = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            window.decorView.systemUiVisibility = if (hide) view else view.inv()
        }
    }
}