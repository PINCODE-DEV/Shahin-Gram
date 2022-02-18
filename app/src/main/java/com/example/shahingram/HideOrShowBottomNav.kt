package com.example.shahingram

import android.os.Build
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.annotation.RequiresApi


class HideOrShowBottomNav(status: String ) {
    private var isUp: Boolean = true

    init {
        if (status == "Show") {
            isUp = false
            checkBottomNavPosition(MainActivity.bottomNav)
        } else {
            isUp = true
            checkBottomNavPosition(MainActivity.bottomNav)
        }

    }

    private fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f,
            0f,
            view.height.toFloat(),
            0f)

        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun slideDown(view: View) {
        view.visibility = View.GONE
        val animate = TranslateAnimation(
            0f,
            0f,
            0f,
            view.height.toFloat())

        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun checkBottomNavPosition(view: View?) {
        if (isUp) {
            slideDown(view!!)
        } else {
            slideUp(view!!)
        }
        isUp = !isUp
    }
}