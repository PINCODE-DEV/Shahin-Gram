package com.example.shahingram.Search

import com.example.shahingram.Home.Post
import com.example.shahingram.Profile.UserProfPrev
import io.reactivex.rxjava3.core.Single

class Search_Repo : Search_DataSource {

    object STon{
        val instance = Search_Repo()
    }

    private val  api = Search_Api.STon.instance
    override fun getAllPosts(userId: String): Single<List<Post>> {
        return api.getAllPosts(userId)
    }

    override fun searchUser(username: String): Single<List<UserProfPrev>> {
        return api.searchUser(username)
    }
}