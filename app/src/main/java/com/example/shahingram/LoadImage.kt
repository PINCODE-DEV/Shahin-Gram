package com.example.shahingram

import android.widget.ImageView
import com.squareup.picasso.Picasso

class LoadImage(val url : String , val imageView : ImageView) {
    init {
        Picasso.get().load(url).into(imageView)
    }
}