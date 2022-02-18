package com.example.shahingram.Like

import com.example.shahingram.Profile.UserProfPrev
import io.reactivex.rxjava3.core.Single

interface Like_DataSource {
    fun getMyFollowers(userId : String) : Single<List<UserProfPrev>>
}