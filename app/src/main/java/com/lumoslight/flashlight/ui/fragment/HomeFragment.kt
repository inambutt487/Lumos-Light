package com.lumoslight.flashlight.ui.fragment

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lumoslight.flashlight.R
import com.lumoslight.flashlight.config.Constants.MORSE
import com.lumoslight.flashlight.data.TinyDatabase
import com.lumoslight.flashlight.databinding.FragmentHomeBinding
import com.lumoslight.flashlight.extentsion.setTapsoundOnClick
import com.lumoslight.flashlight.utill.FlashLight

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var cameraManager: CameraManager
    private var cameraId: String = ""

    private var flashLight: FlashLight = FlashLight()
    private lateinit var tinyDatabase: TinyDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        flashLightListener()
    }

    fun init() {
        activity?.let { activity ->
            cameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            cameraId = cameraManager.cameraIdList[0]
            tinyDatabase = TinyDatabase(activity)

            tinyDatabase.flash = false
            tinyDatabase.sos = false

            flashLight.flashLight(cameraManager, activity, tinyDatabase)
        }
    }

    private fun flashLightListener() {
        activity?.let { activity ->
            binding.iconFlash.setTapsoundOnClick(activity, R.raw.tapsounds)
            binding.iconSos.setTapsoundOnClick(activity, R.raw.tapsounds)

            binding.iconFlash.setOnClickListener {
                if (!tinyDatabase.flash) {
                    if (tinyDatabase.sos) {

                        tinyDatabase.sos = false
                        flashLight.strobocancel()

                        binding.iconSos.setTextColor(activity.getColor(R.color.flash_off))

                    }
                    binding.iconFlash.setImageDrawable(activity.getDrawable(R.drawable.ic_flashlight_on))
                    flashLight.flashLightOn()
                    tinyDatabase.flash = true

                    return@setOnClickListener
                }
                if (tinyDatabase.flash) {

                    flashLight.flashLightOff()
                    tinyDatabase.flash = false

                    binding.iconFlash.setImageDrawable(activity.getDrawable(R.drawable.ic_flashlight_off))

                    return@setOnClickListener
                }
            }

            binding.iconSos.setOnClickListener() {
                if (!tinyDatabase.sos) {

                    if (tinyDatabase.flash) {
                        tinyDatabase.flash = false
                    }

                    binding.iconSos.setTextColor(activity.getColor(R.color.flash_on))
                    binding.iconFlash.setImageDrawable(activity.getDrawable(R.drawable.ic_flashlight_off))
                    flashLight.sos(MORSE, binding.iconFlash)

                    tinyDatabase.sos = true

                    return@setOnClickListener
                }
                if (tinyDatabase.sos) {
                    flashLight.strobocancel()

                    binding.iconSos.setTextColor(activity.getColor(R.color.flash_off))
                    binding.iconFlash.setImageDrawable(activity.getDrawable(R.drawable.ic_flashlight_off))

                    tinyDatabase.sos = false

                    return@setOnClickListener
                }

            }
        }
    }
}