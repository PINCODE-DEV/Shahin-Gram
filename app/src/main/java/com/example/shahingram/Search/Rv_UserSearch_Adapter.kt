package com.example.shahingram.Search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.shahingram.LoadImage
import com.example.shahingram.LoadImageProfile
import com.example.shahingram.Profile.UserProfPrev
import com.example.shahingram.PublicViewHolder
import com.example.shahingram.R
import com.example.shahingram.Retrofit.ApiClient
import kotlinx.android.synthetic.main.user_prof_prev.view.*

class Rv_UserSearch_Adapter(val context : Context, val users : List<UserProfPrev>) : RecyclerView.Adapter<PublicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicViewHolder {
        return PublicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_prof_prev,parent,false))
    }

    override fun onBindViewHolder(holder: PublicViewHolder, position: Int) {
        val model = users[position]
        val view = holder.itemView

        LoadImageProfile("${ApiClient().baseUrl}${model.profile}",view.img_userProfPrev_prof)
        view.txt_userProfPrev_username.text = model.username

        view.cv_userProfPrev_parent.setOnClickListener {
            val prof = UserProfPrev(model.profile,model.username,model.userId)
            val nextAction = Fragment_SearchDirections.actionFragmentSearchToFragmentUserProf(prof)
            Navigation.findNavController(view).navigate(nextAction)
        }

        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_posts)
        view.cv_userProfPrev_parent.startAnimation(animation)
    }

    override fun getItemCount(): Int = users.size
}