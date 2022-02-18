package com.example.shahingram.Search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.shahingram.Home.Post
import com.example.shahingram.LoadImage
import com.example.shahingram.PublicViewHolder
import com.example.shahingram.R
import com.example.shahingram.Retrofit.ApiClient
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.post_explorer_item.view.*
import kotlinx.android.synthetic.main.posts_item.view.*

class Rv_PostsSearch_Adapter(
    val context: Context,
    val posts: List<Post>,
    val onPostClick: OnPostClick,
) : RecyclerView.Adapter<PublicViewHolder>() {

    interface OnPostClick {
        fun onClick(view: View, post: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicViewHolder {
        return PublicViewHolder(LayoutInflater.from(context).inflate(R.layout.post_explorer_item,parent,false))
    }

    override fun onBindViewHolder(holder: PublicViewHolder, position: Int) {
        val post = posts[position]
        val view = holder.itemView
        val imgPrev = view.findViewById<RoundedImageView>(R.id.img_postPreViewItem_image)
        LoadImage(ApiClient().baseUrl+post.image,imgPrev)

        imgPrev.setOnClickListener {
            onPostClick.onClick(it,post)
        }

        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_posts)
        view.img_postPreViewItem_image.startAnimation(animation)
    }

    override fun getItemCount(): Int = posts.size
}