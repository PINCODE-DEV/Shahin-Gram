package com.example.shahingram

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment

class SetStatusBarColor(activity: Activity) {
    init {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}