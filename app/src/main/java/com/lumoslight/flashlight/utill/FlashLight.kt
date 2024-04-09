package com.lumoslight.flashlight.utill

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.lumoslight.flashlight.R
import com.lumoslight.flashlight.data.TinyDatabase

class FlashLight() {
    private var cameraManager: CameraManager? = null
    private var context: Context? = null
    var speed = 0.0
    var thread: Thread? = null

    private lateinit var tinyDatabase: TinyDatabase

    fun flashLight(cameraManager: CameraManager?, context: Context?, tinyDatabase: TinyDatabase) {
        this.cameraManager = cameraManager
        this.context = context
        this.tinyDatabase = tinyDatabase
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun flashLightOn() {
        try {
            val cameraId = cameraManager!!.cameraIdList[0]
            cameraManager!!.setTorchMode(cameraId, true)
        } catch (_: Exception) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun flashLightOff() {
        try {
            val cameraId = cameraManager!!.cameraIdList[0]
            cameraManager!!.setTorchMode(cameraId, false)
        } catch (_: Exception) {
        }
    }

    fun updateSpeed() {
        val sos_speed = 50.0
        speed = 1 / (sos_speed / 100);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun sos(morse: String, status: ImageView) {
        if(tinyDatabase.sos){
            status.setImageDrawable(context!!.getDrawable(R.drawable.ic_flashlight_off))
        }
        //sos thread
        thread = object : Thread() {
            override fun run() {
                try {
                    val delay = 1000
                    while (true) {
                        sosToFlash(morse,status)      //call sos function
                        sleep(delay.toLong())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        (this.thread as Thread).start()
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    fun strobocancel() {
        //interrupt thread
        if (thread != null) {
            if (!thread!!.isInterrupted) {
                thread!!.interrupt();
                flashLightOff()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SuspiciousIndentation")
    fun sosToFlash(morse: String, status: ImageView) {
        if(tinyDatabase.sos){
            status.setImageDrawable(context!!.getDrawable(R.drawable.ic_flashlight_off))
        }
        //sos thread
        outerloop@for (x in morse.indices) {
            //interrupt thread when true
            if (tinyDatabase.flash&&!tinyDatabase.sos) thread?.interrupt()
            if (morse[x] == '.') {
                flashLightOn()
                Thread.sleep(200)
                flashLightOff()
                Thread.sleep(200)

            }
            if (morse[x] == '-') {
                flashLightOn()
                Thread.sleep(500)
                flashLightOff()
                Thread.sleep(500)
            }
            if (morse[x] == ' ') {
                Thread.sleep(300)
                Thread.sleep(300)
            }
        }
    }
}