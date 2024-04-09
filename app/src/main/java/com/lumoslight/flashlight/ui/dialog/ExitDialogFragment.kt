package com.lumoslight.flashlight.ui.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.lumoslight.flashlight.R
import com.lumoslight.flashlight.databinding.DialogExitBinding
import com.lumoslight.flashlight.extentsion.setTapsoundOnClick


class ExitDialogFragment : DialogFragment() {

    private lateinit var binding: DialogExitBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogExitBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.let { dialog ->
            activity?.let { activity ->
                binding.btnNo.setTapsoundOnClick(activity, R.raw.tapsounds)
                binding.btnYes.setTapsoundOnClick(activity, R.raw.tapsounds)

                binding.btnNo.setOnClickListener {
                    dialog.dismiss()
                }

                binding.btnYes.setOnClickListener {
                    activity.finish()
                }

                dialog.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                dialog.window?.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                );
            }
        }
    }

}