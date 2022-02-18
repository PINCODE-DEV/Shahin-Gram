package com.example.shahingram.Like

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.example.shahingram.LoadImageProfile
import com.example.shahingram.Profile.UserProfPrev
import com.example.shahingram.PublicViewHolder
import com.example.shahingram.R
import com.example.shahingram.Retrofit.ApiClient
import com.example.shahingram.SetYoYo
import kotlinx.android.synthetic.main.user_prof_prev.view.*

class Rv_MyFollowers_Adapter(val context : Context , val followers : List<UserProfPrev>) : RecyclerView.Adapter<PublicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicViewHolder {
        return PublicViewHolder(LayoutInflater.from(context).inflate(R.layout.user_prof_prev,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PublicViewHolder, position: Int) {
        val follower = followers[position]
        val view = holder.itemView

        LoadImageProfile("${ApiClient().baseUrl}${follower.profile}",view.img_userProfPrev_prof)
        view.txt_userProfPrev_username.text = "${follower.username}   started following you!"

        SetYoYo(Techniques.ZoomIn,800,view.cv_userProfPrev_parent)
    }

    override fun getItemCount(): Int = followers.size
}