package com.example.shahingram.Home

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.shahingram.LoadImageProfile
import com.example.shahingram.PublicViewHolder
import com.example.shahingram.R
import com.example.shahingram.Retrofit.ApiClient
import kotlinx.android.synthetic.main.rv_story_item.view.*

class Rv_Stories_Adapter(val context: Context, val stories: List<Story> , val onStoryClick: OnStoryClick) :
    RecyclerView.Adapter<PublicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicViewHolder {
        return PublicViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_story_item, parent, false))
    }

    override fun onBindViewHolder(holder: PublicViewHolder, position: Int) {
        val view = holder.itemView
        val story = stories[position]

        view.txt_rvStory_username.text = story.username
        LoadImageProfile(ApiClient().baseUrl + story.profile,view.img_rvStory_img)
        val timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                view.ll_rvStory_profile.visibility = View.VISIBLE
                view.txt_rvStory_username.visibility = View.VISIBLE
                val animation = AnimationUtils.loadAnimation(context, R.anim.anim_stories)
                view.rl_storyItem_parent.startAnimation(animation)
            }
        }
        timer.start()

        view.rl_storyItem_parent.setOnClickListener {
            onStoryClick.onClick(it,story)
        }
    }

    override fun getItemCount(): Int = stories.size

    interface OnStoryClick{
        fun onClick(view : View , story : Story)
    }
}