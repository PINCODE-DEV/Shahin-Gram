package com.example.shahingram

import android.widget.ImageView
import com.squareup.picasso.Picasso

class LoadImageProfile(url : String, val imageView : ImageView) {
    init {
        Picasso.get().load(url).fit().centerCrop().into(imageView)
    }
}