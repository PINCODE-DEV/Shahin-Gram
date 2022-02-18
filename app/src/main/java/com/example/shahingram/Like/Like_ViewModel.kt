package com.example.shahingram.Like

import android.util.Log
import com.example.shahingram.Profile.UserProfPrev
import io.reactivex.rxjava3.schedulers.Schedulers

class Like_ViewModel {
    object STon {
        val instance = Like_ViewModel()
    }
    val repo = Like_Repo.STon.instance

    fun getMyFollowers(userId: String, onMyFollowersReceived: OnMyFollowersReceived) {
        repo.getMyFollowers(userId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onMyFollowersReceived.onReceived(it)
                },
                {
                    Log.i("LOG", "getMyFollowers: "+it.message)
                }
            )
    }

    interface OnMyFollowersReceived {
        fun onReceived(followers: List<UserProfPrev>)
    }
}