package com.lumoslight.flashlight.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lumoslight.flashlight.R
import com.lumoslight.flashlight.config.Constants
import com.lumoslight.flashlight.data.TinyDatabase
import com.lumoslight.flashlight.databinding.FragmentSettingBinding
import com.lumoslight.flashlight.extentsion.mainActivity
import com.lumoslight.flashlight.extentsion.themeMode
import com.lumoslight.flashlight.ui.WebActivity


class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    private lateinit var tinyDatabase: TinyDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listener()
    }

    private fun init() {
        activity?.let { activity ->
            tinyDatabase = TinyDatabase(activity)

            if (tinyDatabase.dark_theme) {
                binding.dark.isChecked = true
            } else {
                binding.light.isChecked = true
            }
        }
    }

    private fun listener() {
        activity?.let { activity ->

            binding.privacyPolicy.setOnClickListener {
                startWeb(activity.getString(R.string.privacy_policy_url))
            }
            binding.aboutUs.setOnClickListener {
                startWeb(activity.getString(R.string.about_us_url))
            }

            binding.light.setOnCheckedChangeListener { view, check ->
                if (check) {
                    tinyDatabase.dark_theme = false
                    mainActivity?.themeMode()
                }
            }

            binding.dark.setOnCheckedChangeListener { view, check ->
                if (check) {
                    tinyDatabase.dark_theme = true
                    mainActivity?.themeMode()
                }
            }
        }
    }

    private fun startWeb(url: String) {
        activity?.let { activity ->
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra(Constants.URL, url)
            startActivity(intent)
        }
    }
}