package com.example.shahingram.Home

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shahingram.HideOrShowBottomNav
import com.example.shahingram.LoadImage
import com.example.shahingram.MainActivity
import com.example.shahingram.LoadImageProfile
import com.example.shahingram.R
import com.example.shahingram.Retrofit.ApiClient
import kotlinx.android.synthetic.main.fragment_story.view.*

open class Fragment_Story() : Fragment() {
    private lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        HideOrShowBottomNav("Hide")
        mView = inflater.inflate(R.layout.fragment_story,container,false)
        SetupViews()

    return mView;
    }

    private fun SetupViews() {
        MainActivity.currentFrag = "Story"
        val story = arguments?.getParcelable<Parcelable>("story") as Story

        LoadImage(ApiClient().baseUrl+story.story,mView.img_storyFragment_story)
        LoadImageProfile(ApiClient().baseUrl+story.profile,mView.img_storyFragment_prof)
        mView.txt_storyFragment_username.text = story.username
    }
}