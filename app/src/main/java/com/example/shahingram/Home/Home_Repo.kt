package com.example.shahingram.Home

import com.example.shahingram.Like.ILikedThisPost
import com.example.shahingram.Profile.JsonOk
import io.reactivex.rxjava3.core.Single

class Home_Repo : Home_DataSource{

    object STon{
        val instance = Home_Repo()
    }

    private val api = Home_Api.STon.instance
    override fun getStories(): Single<List<Story>> {
        return api.getStories()
    }

    override fun getPosts(userId: String): Single<List<Post>> {
        return api.getPosts(userId)
    }

    override fun likePost(userId: String, postId: String): Single<JsonOk> {
        return api.likePost(userId, postId)
    }

    override fun savePost(userId: String, postId: String): Single<JsonStatus> {
        return api.savePost(userId, postId)
    }

    override fun unSavePost(userId: String, postId: String): Single<JsonStatus> {
        return api.unSavePost(userId, postId)
    }

    override fun unLikePost(userId: String, postId: String): Single<JsonOk> {
        return api.unLikePost(userId, postId)
    }

    override fun getPostComments(postId: String): Single<List<Comment>> {
        return api.getPostComments(postId)
    }

    override fun getLikeCount(postId: String): Single<LikeCount> {
        return api.getLikeCount(postId)
    }

    override fun addComment(userId: String, postId: String, comment: String): Single<JsonStatus> {
        return api.addComment(userId, postId, comment)
    }

    override fun deletePost(postId: String): Single<JsonStatus> {
        return api.deletePost(postId)
    }
}