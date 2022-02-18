package com.example.shahingram.Search

import com.example.shahingram.Home.Post
import com.example.shahingram.Profile.UserProfPrev
import io.reactivex.rxjava3.core.Single

interface Search_DataSource {
    fun getAllPosts(userId : String) : Single<List<Post>>
    fun searchUser(username : String) : Single<List<UserProfPrev>>
}