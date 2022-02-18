package com.example.shahingram.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.shahingram.LoadImageProfile
import com.example.shahingram.PublicViewHolder
import com.example.shahingram.R
import com.example.shahingram.Retrofit.ApiClient
import kotlinx.android.synthetic.main.comment_tem.view.*

class Rv_Comments_Adapter(val context: Context, val comments: List<Comment>) :
    RecyclerView.Adapter<PublicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicViewHolder {
        return PublicViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_tem, parent, false))
    }

    override fun onBindViewHolder(holder: PublicViewHolder, position: Int) {
        val view = holder.itemView
        val comment = comments[position]

        view.txt_commentItem_username.text = comment.username
        LoadImageProfile(ApiClient().baseUrl + comment.profileImg,view.img_commentItem_prof)
        view.txt_commentItem_comment.text = comment.comment

        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_posts)
        view.rl_commentItem_parent.startAnimation(animation)
    }

    override fun getItemCount(): Int = comments.size
}