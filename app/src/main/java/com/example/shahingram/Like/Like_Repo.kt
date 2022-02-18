package com.example.shahingram.Like

import com.example.shahingram.Profile.UserProfPrev
import io.reactivex.rxjava3.core.Single

class Like_Repo : Like_DataSource {
    object STon{
        val instance = Like_Repo()
    }
    val api = Like_Api.STon.instance
    override fun getMyFollowers(userId: String): Single<List<UserProfPrev>> {
        return api.getMyFollowers(userId)
    }
}