package com.lumoslight.flashlight.extentsion

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.media.MediaPlayer
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.lumoslight.flashlight.data.TinyDatabase
import com.lumoslight.flashlight.ui.HomeActivity


//Activity - Make FullScreen
fun AppCompatActivity.makeFullScreen() {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun AppCompatActivity.themeMode() {
    if (TinyDatabase(this).dark_theme){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }else{
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}

fun View.setTapsoundOnClick(context: Context, rawResourceId: Int) {
    val mp: MediaPlayer = MediaPlayer.create(context, rawResourceId)
    this.setOnClickListener {
        mp.start()
    }
}

fun Context.inAppUpdate(activity: Activity) {
    val REQUEST_CODE = 101
    val appUpdateManager = AppUpdateManagerFactory.create(this)
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo
    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
        ) {
            try {
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    activity,
                    // Include a request code to later monitor this update request.
                    REQUEST_CODE
                )
            } catch (ex: IntentSender.SendIntentException) {
            }
        }
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        this.requestLayout()
    }
}

val Fragment.mainActivity: HomeActivity?
    get() {
        return activity as HomeActivity?
    }