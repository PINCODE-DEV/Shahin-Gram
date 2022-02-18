package com.example.shahingram.Home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.daimajia.androidanimations.library.Techniques
import com.example.shahingram.HideOrShowBottomNav
import com.example.shahingram.MainActivity
import com.example.shahingram.Profile.Fragment_ProfileDirections
import com.example.shahingram.R
import com.example.shahingram.SetYoYo
import kotlinx.android.synthetic.main.posts_item.view.*
import kotlinx.android.synthetic.main.splash_screen.view.*

class Fragment_SplashScreen : Fragment() {
    lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.splash_screen,container,false)
        HideOrShowBottomNav("Hide")
        MainActivity.currentFrag = "Splash"

        SetYoYo(Techniques.ZoomIn,800,mView.img_splashScreenFragment_logo)
        SetYoYo(Techniques.Flash,800,mView.txt_splashScreenFragment_logo)
        SetYoYo(Techniques.FlipInX,800,mView.txt_splashScreenFragment_text)

        object: CountDownTimer(1200, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val progressAnim = AnimationUtils.loadAnimation(context, R.anim.anim_posts)
                mView.progress_splashScreenFragment_progress.startAnimation(progressAnim)
                mView.progress_splashScreenFragment_progress.visibility = View.VISIBLE
            }
        }.start()

        object: CountDownTimer(3500, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val nextAction = Fragment_SplashScreenDirections.actionFragmentSplashToFragmentHome()
                Navigation.findNavController(mView).navigate(nextAction)
            }
        }.start()

        return mView
    }

}