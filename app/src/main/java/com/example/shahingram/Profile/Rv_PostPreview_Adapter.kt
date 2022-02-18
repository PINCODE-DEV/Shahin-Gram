package com.example.shahingram.Profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.shahingram.Home.Post
import com.example.shahingram.LoadImage
import com.example.shahingram.PublicViewHolder
import com.example.shahingram.R
import com.example.shahingram.Retrofit.ApiClient
import kotlinx.android.synthetic.main.post_explorer_item.view.*
class Rv_PostPreview_Adapter(val context : Context , val posts : List<Post> , val onPostClick: OnPostClick) : RecyclerView.Adapter<PublicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicViewHolder {
        return PublicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_preview_item,parent,false))
    }

    override fun onBindViewHolder(holder: PublicViewHolder, position: Int) {
        val view = holder.itemView
        val imgPreview = view.findViewById<ImageView>(R.id.img_postPreViewItem_image)

        LoadImage("${ApiClient().baseUrl}${posts[position].image}",imgPreview)

        imgPreview.setOnClickListener {
            onPostClick.onClick(it,posts[position])
        }
        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_posts)
        view.img_postPreViewItem_image.startAnimation(animation)

    }

    override fun getItemCount(): Int = posts.size

    interface OnPostClick{
        fun onClick(view : View, post : Post)
    }
}