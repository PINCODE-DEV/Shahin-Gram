package com.example.shahingram.Profile

import android.util.Log
import com.example.shahingram.Home.JsonStatus
import com.example.shahingram.Home.Post
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Profile_ViewModel {

    object STon {
        val instance = Profile_ViewModel()
    }

    private val repo = Profile_Repo.STon.instance

    fun getUserInfo(username: String, myId : String, onUSerInfoReceived: OnUSerInfoReceived) {
        repo.getUserInfo(username,myId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onUSerInfoReceived.onReceived(it)
                },
                {
                    Log.i("LOG", "getUserInfo: ${it.message}")
                    onUSerInfoReceived.onFailure()
                })
    }

    fun getMyPosts(userId: String, onGetPostsReceived: OnGetPostsReceived) {
        repo.getMyPosts(userId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onGetPostsReceived.onReceived(it)
                },
                {
                    onGetPostsReceived.onFailure()
                }
            )
    }

    fun updateProfile(
        oldUsername: String,
        oldPass: String,
        newUsername: String,
        newPass: String,
        email: String,
        bio: String,
        onUpdateProfile: OnUpdateProfile,
    ) {
        repo.updateProfile(oldUsername, oldPass, newUsername, newPass, email, bio)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onUpdateProfile.onUpdated()
                },
                {
                    onUpdateProfile.onFailure()
                })
    }

    fun updateProfileImage(
        username: RequestBody,
        password: RequestBody,
        profileImage: MultipartBody.Part,
        onUpdateProfileImage: OnUpdateProfileImage,
    ) {
        repo.updateProfileImage(username, password, profileImage)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onUpdateProfileImage.onUpdated()
                },
                {
                    onUpdateProfileImage.onFailure()
                }
            )
    }

    fun getSavedPosts(userId : String , onSavedPostsReceived: OnSavedPostsReceived){
        repo.getSavedPosts(userId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onSavedPostsReceived.onReceived(it)
                },
                {
                    Log.i("LOG", "getSavedPosts: ${it.message}")
                }
            )
    }

    fun followUser(myId: String, userId: String, onFollowUser: OnFollowUser){
        repo.followUser(myId, userId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onFollowUser.onFollow(it.status.toString())
                },
                {
                    Log.i("LOG", "followUser: ${it.message}")
                }
            )
    }

    fun unFollowUser(myId: String, userId: String, onUnFollowUser: OnUnFollowUser){
        repo.unFollowUser(myId, userId)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                {
                    onUnFollowUser.onFollow(it.status.toString())
                },
                {
                    Log.i("LOG", "followUser: ${it.message}")
                }
            )
    }

    interface OnUSerInfoReceived {
        fun onReceived(model: UserInfo)
        fun onFailure()
    }

    interface OnGetPostsReceived {
        fun onReceived(posts: List<Post>)
        fun onFailure()
    }

    interface OnUpdateProfile {
        fun onUpdated()
        fun onFailure()
    }

    interface OnUpdateProfileImage {
        fun onUpdated()
        fun onFailure()
    }

    interface OnSavedPostsReceived{
        fun onReceived(posts : List<Post>)
    }

    interface OnFollowUser{
        fun onFollow(status: String)
    }

    interface OnUnFollowUser{
        fun onFollow(status: String)
    }

}