package com.example.shahingram.Profile

import com.example.shahingram.Home.JsonStatus
import com.example.shahingram.Home.Post
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Profile_Repo : Profile_DataSource {

    object STon {
        val instance = Profile_Repo()
    }

    val api = Profile_Api.STon.instance
    override fun getUserInfo(username: String, myId: String): Single<UserInfo> {
        return api.getUserInfo(username, myId)
    }

    override fun getMyPosts(userId: String): Single<List<Post>> {
        return api.getMyPosts(userId)
    }

    override fun updateProfile(
        oldUsername: String,
        oldPass: String,
        newUsername: String,
        newPass: String,
        email: String,
        bio: String,
    ): Single<JsonOk> {
        return api.updateProfile(oldUsername, oldPass, newUsername, newPass, email, bio)
    }

    override fun updateProfileImage(
        username: RequestBody,
        password: RequestBody,
        imageProfile: MultipartBody.Part,
    ): Single<JsonOk> {
        return api.updateProfileImage(username, password, imageProfile)
    }

    override fun getSavedPosts(userId: String): Single<List<Post>> {
        return api.getSavedPosts(userId)
    }

    override fun followUser(myId: String, userId: String): Single<JsonStatus> {
        return api.followUser(myId, userId)
    }

    override fun unFollowUser(myId: String, userId: String): Single<JsonStatus> {
        return api.unFollowUser(myId, userId)
    }
}