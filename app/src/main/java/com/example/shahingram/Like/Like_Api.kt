package com.example.shahingram.Like

import com.example.shahingram.Profile.UserProfPrev
import com.example.shahingram.Retrofit.ApiProvider
import io.reactivex.rxjava3.core.Single

class Like_Api : Like_DataSource{
    object STon{
        val instance = Like_Api()
    }
    val api = ApiProvider.STon.instance
    override fun getMyFollowers(userId: String): Single<List<UserProfPrev>> {
        return api.getFollowers(userId)
    }
}