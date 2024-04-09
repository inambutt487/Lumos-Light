package com.lumoslight.flashlight.data

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.preference.PreferenceManager
import com.lumoslight.flashlight.config.Constants.BRIGHT_COLOR
import com.lumoslight.flashlight.config.Constants.FLASH
import com.lumoslight.flashlight.config.Constants.SOS
import com.lumoslight.flashlight.config.Constants.THEME

class TinyDatabase(context: Context) {
    private val preference = PreferenceManager.getDefaultSharedPreferences(context)
    val editor: SharedPreferences.Editor =  preference.edit()

    var flash: Boolean
        get() = preference.getBoolean(FLASH, false)
        set(flash) = editor.putBoolean(FLASH, flash).apply()

    var sos: Boolean
        get() = preference.getBoolean(SOS, false)
        set(sos) = editor.putBoolean(SOS, sos).apply()

    var dark_theme: Boolean
        get() = preference.getBoolean(THEME, false)
        set(theme) = editor.putBoolean(THEME, theme).apply()
}