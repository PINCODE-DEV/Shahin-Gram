package com.example.shahingram.Home

import com.example.shahingram.Profile.JsonOk
import com.example.shahingram.Retrofit.ApiProvider
import io.reactivex.rxjava3.core.Single

class Home_Api : Home_DataSource {

    object STon {
        val instance = Home_Api()
    }

    private val apiService = ApiProvider.STon.instance
    override fun getStories(): Single<List<Story>> {
        return apiService.getStories((1..100).random())
    }

    override fun getPosts(userId: String): Single<List<Post>> {
        return apiService.getPosts(userId)
    }

    override fun likePost(userId: String, postId: String): Single<JsonOk> {
        return apiService.likePost(userId, postId)
    }

    override fun savePost(userId: String, postId: String): Single<JsonStatus> {
        return apiService.savePost(userId, postId)
    }

    override fun unSavePost(userId: String, postId: String): Single<JsonStatus> {
        return apiService.unSavePost(userId, postId)
    }

    override fun unLikePost(userId: String, postId: String): Single<JsonOk> {
        return apiService.unLikePost(userId, postId)
    }

    override fun getPostComments(postId: String): Single<List<Comment>> {
        return apiService.getComments(postId)
    }

    override fun getLikeCount(postId: String): Single<LikeCount> {
        return apiService.likeCount(postId)
    }

    override fun addComment(userId: String, postId: String, comment: String): Single<JsonStatus> {
        return apiService.addComment(userId, postId, comment)
    }

    override fun deletePost(postId: String): Single<JsonStatus> {
        return apiService.deletePost(postId)
    }

}