package com.example.shahingram.Home

import com.example.shahingram.Like.ILikedThisPost
import com.example.shahingram.Profile.JsonOk
import io.reactivex.rxjava3.core.Single

interface Home_DataSource {
    fun getStories() : Single<List<Story>>
    fun getPosts(userId : String) : Single<List<Post>>
    fun likePost(userId : String , postId : String) : Single<JsonOk>
    fun unLikePost(userId : String , postId : String) : Single<JsonOk>
    fun savePost(userId : String , postId : String) : Single<JsonStatus>
    fun unSavePost(userId : String , postId : String) : Single<JsonStatus>
    fun getPostComments(postId: String) : Single<List<Comment>>
    fun getLikeCount(postId: String) : Single<LikeCount>
    fun addComment(userId : String , postId : String , comment : String) : Single<JsonStatus>
    fun deletePost(postId : String) : Single<JsonStatus>
}