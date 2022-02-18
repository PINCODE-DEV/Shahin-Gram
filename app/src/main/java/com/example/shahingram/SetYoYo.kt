package com.example.shahingram

import android.view.View
import android.view.animation.BounceInterpolator
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import java.time.Duration

class SetYoYo(techniques: Techniques , duration : Long , view : View) {
    init {
        YoYo.with(techniques)
            .duration(duration)
            .playOn(view)
    }
}