package com.example.shahingram.Search

import android.util.Log
import com.example.shahingram.Home.Post
import com.example.shahingram.Profile.UserProfPrev
import io.reactivex.rxjava3.schedulers.Schedulers

class Search_ViewModel {
    object STon {
        val instance = Search_ViewModel()
    }

    val repo = Search_Repo.STon.instance

    fun getPosts(userId: String , onPostsReceived: OnPostsReceived) {
        repo.getAllPosts(userId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                onPostsReceived.onReceived(it)
                },
                {
                    onPostsReceived.onFailure(it.message.toString())
                }
            )
    }

    fun searchUser(username : String , onSearchUserReceived: OnSearchUserReceived){
        repo.searchUser(username)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onSearchUserReceived.onReceived(it)
                },
                {
                    Log.i("LOG", "searchUser: ${it.message}")
                }
            )
    }

    interface OnPostsReceived{
        fun onReceived(posts : List<Post>)
        fun onFailure(message : String)
    }

    interface OnSearchUserReceived{
        fun onReceived(users : List<UserProfPrev>)
    }
}