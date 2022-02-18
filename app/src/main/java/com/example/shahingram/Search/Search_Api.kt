package com.example.shahingram.Search

import com.example.shahingram.Home.Post
import com.example.shahingram.Profile.UserProfPrev
import com.example.shahingram.Retrofit.ApiProvider
import io.reactivex.rxjava3.core.Single

class Search_Api : Search_DataSource{

    object STon{
        val instance = Search_Api()
    }

    private val api = ApiProvider.STon.instance
    override fun getAllPosts(userId: String): Single<List<Post>> {
        return api.getPosts(userId)
    }

    override fun searchUser(username: String): Single<List<UserProfPrev>> {
        return api.searchUsers(username)
    }
}