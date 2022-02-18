package com.example.shahingram.Profile

import com.example.shahingram.Home.JsonStatus
import com.example.shahingram.Home.Post
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface Profile_DataSource {
    fun getUserInfo(username: String , myId : String): Single<UserInfo>
    fun getMyPosts(userId: String): Single<List<Post>>
    fun updateProfile(
        oldUsername: String,
        oldPass: String,
        newUsername: String,
        newPass: String,
        email: String,
        bio: String,
    ): Single<JsonOk>

    fun updateProfileImage(
        username: RequestBody,
        password: RequestBody,
        imageProfile: MultipartBody.Part,
    ): Single<JsonOk>

    fun getSavedPosts(userId : String) : Single<List<Post>>

    fun followUser(myId: String , userId: String) : Single<JsonStatus>
    fun unFollowUser(myId: String , userId: String) : Single<JsonStatus>
}