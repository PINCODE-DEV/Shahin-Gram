package com.example.shahingram.Profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.example.shahingram.*
import com.example.shahingram.Home.Post
import com.example.shahingram.Retrofit.ApiClient
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class Fragment_Profile() : Fragment() {
    private lateinit var prefInfo: SharedPreferences
    private lateinit var mView: View
    private lateinit var btnEditProf: AppCompatButton
    private lateinit var btnFollow: AppCompatButton
    private var username = ""
    private var userId = ""
    private var myUserId = ""
    private val viewModel = Profile_ViewModel.STon.instance
    private lateinit var mModel: UserInfo
    private lateinit var profInfo: UserProfPrev
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mView = inflater.inflate(R.layout.fragment_profile, container, false)
        if (MainActivity.currentFrag == "Posts" || MainActivity.currentFrag == "Saved")
            HideOrShowBottomNav("Show")

        SetupViews()

        return mView
    }

    override fun onResume() {
        prefInfo = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        myUserId = prefInfo.getString("userId", "")!!
        if (profInfo.username == prefInfo.getString("username", ""))
            HideOrShowBottomNav("Hide")
        if (profInfo.username == "me" || profInfo.username == prefInfo.getString("username", "")) {
            mView.btn_profileFragment_follow.visibility = View.GONE
            if (prefInfo.getString("username", "")!!.isNotEmpty()) {
                username = prefInfo.getString("username", "")!!
                txt_profileFragment_username.text = username
            }
        } else {
            mView.btn_profileFragment_follow.visibility = View.VISIBLE
            MainActivity.currentFrag = "UserProfile"
            HideOrShowBottomNav("Hide")
        }
        GetUserInfo()
        super.onResume()
    }

    private fun SetupViews() {
        val rvPost = mView.findViewById<RecyclerView>(R.id.rv_profileFragment_posts)
        btnEditProf = mView.findViewById(R.id.btn_profileFragment_editProf);
        btnFollow = mView.findViewById(R.id.btn_profileFragment_follow);

        prefInfo = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        profInfo = arguments?.getParcelable<Parcelable>("profInfo") as UserProfPrev

        if (profInfo.username != "me")
            btnEditProf.visibility = View.GONE

        SetYoYo(Techniques.FlipInX, 1500, mView.ll_profileFragment_postCount)
        SetYoYo(Techniques.FlipInX, 1500, mView.ll_profileFragment_followers)
        SetYoYo(Techniques.FlipInX, 1500, mView.ll_profileFragment_following)
        SetYoYo(Techniques.ZoomIn, 800, mView.img_profileFragment_profile)
        SetYoYo(Techniques.FadeIn, 1500, mView.btn_profileFragment_editProf)
        SetYoYo(Techniques.FadeIn, 1000, btnFollow)
        SetYoYo(Techniques.ZoomIn, 1000, mView.txt_profileFragment_username)
        SetYoYo(Techniques.Shake, 1000, mView.txt_profileFragment_bio)
        SetYoYo(Techniques.Swing, 1000, mView.img_profileFragment_saved)

        rvPost.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)

        if (profInfo.username == "me" || profInfo.username == prefInfo.getString("username", "")) {
            btnFollow.visibility = View.GONE
            prefInfo = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            if (prefInfo.getString("username", "")!!.isNotEmpty()) {
                userId = prefInfo.getString("userId", "")!!
                username = prefInfo.getString("username", "")!!
                mView.txt_profileFragment_username.text = username
            }
        } else {
            userId = profInfo.userId!!
            username = profInfo.username!!
            mView.txt_profileFragment_username.text = username
        }

        viewModel.getMyPosts(userId, object : Profile_ViewModel.OnGetPostsReceived {
            override fun onReceived(posts: List<Post>) {
                if (posts[0].username!!.isEmpty()){
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(),"شما هنوز پستی ثبت نکرده اید!",Toast.LENGTH_LONG).show()
                        progress_profileFragment_progress.visibility = View.GONE
                    }
                }else{
                    requireActivity().runOnUiThread(Runnable {
                        rvPost.adapter = Rv_PostPreview_Adapter(requireContext(),
                            posts,
                            object : Rv_PostPreview_Adapter.OnPostClick {
                                override fun onClick(view: View, post: Post) {
                                    val nextAction =
                                        Fragment_ProfileDirections.actionFragmentProfileToFragmentPosts(
                                            post)
                                    Navigation.findNavController(view).navigate(nextAction)
                                }
                            })
                        progress_profileFragment_progress.visibility = View.GONE
                        rvPost.visibility = View.VISIBLE
                    })
                }
            }

            override fun onFailure() {
                requireActivity().runOnUiThread(Runnable {
                    progress_profileFragment_progress.visibility = View.GONE
                    Toast.makeText(requireContext(), "خطا در ارتباط با سرور3!", Toast.LENGTH_LONG)
                        .show()
                })
            }
        })

        btnEditProf.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            intent.putExtra("profile", mModel.profileImg)
            intent.putExtra("bio", mModel.bio)
            intent.putExtra("email", mModel.email)
            startActivity(intent)
        }

        mView.img_profileFragment_saved.setOnClickListener {
            val nextAction = Fragment_ProfileDirections.actionFragmentProfileToFragmentSaved()
            Navigation.findNavController(it).navigate(nextAction)
        }

    }

    private fun GetUserInfo() {
        viewModel.getUserInfo(username, myUserId, object : Profile_ViewModel.OnUSerInfoReceived {
            @SuppressLint("SetTextI18n")
            override fun onReceived(model: UserInfo) {
                mModel = model
                requireActivity().runOnUiThread(Runnable {
                    LoadImageProfile("${ApiClient().baseUrl}${model.profileImg}",
                        img_profileFragment_profile)
                    txt_profileFragment_postCount.text = mModel.postCount
                    txt_profileFragment_followersCount.text = mModel.followersCount
                    txt_profileFragment_followingCount.text = mModel.followingCount
                    txt_profileFragment_bio.text = model.bio

                    if (profInfo.username != "me" && profInfo.username != prefInfo.getString("username",
                            "")
                    ) {
                        btnFollow.visibility = View.VISIBLE
                        SetYoYo(Techniques.FadeIn, 2000, btnFollow)
                        if (model.iFollowed == "1") {
                            btnFollow.text = "Followed"
                            btnFollow.setTextColor(ContextCompat.getColor(requireContext(),
                                R.color.black))
                            btnFollow.setBackgroundResource(R.drawable.shape_btn_edit_prof)
                        }
                        btnFollow.setOnClickListener {
                            if (model.iFollowed == "0") {
                                btnFollow.text = "Followed"
                                btnFollow.setTextColor(ContextCompat.getColor(requireContext(),
                                    R.color.black))
                                btnFollow.setBackgroundResource(R.drawable.shape_btn_edit_prof)

                                viewModel.followUser(myUserId,
                                    userId,
                                    object : Profile_ViewModel.OnFollowUser {
                                        override fun onFollow(status: String) {
                                            if (status == "success") {
                                                val count =
                                                    mView.txt_profileFragment_followersCount.text.toString()
                                                        .toInt()
                                                requireActivity().runOnUiThread {
                                                    mView.txt_profileFragment_followersCount.text =
                                                        (count + 1).toString()
                                                }
                                            }
                                        }
                                    })

                                SetYoYo(Techniques.Swing, 500, btnFollow)
                                model.iFollowed = "1"

                            } else {
                                btnFollow.text = "Follow"
                                btnFollow.setTextColor(ContextCompat.getColor(requireContext(),
                                    R.color.white))
                                btnFollow.setBackgroundResource(R.drawable.shape_btn_follow)

                                viewModel.unFollowUser(myUserId,
                                    userId,
                                    object : Profile_ViewModel.OnUnFollowUser {
                                        override fun onFollow(status: String) {
                                            if (status == "success") {
                                                val count =
                                                    mView.txt_profileFragment_followersCount.text.toString()
                                                        .toInt()
                                                requireActivity().runOnUiThread {
                                                    mView.txt_profileFragment_followersCount.text =
                                                        (count - 1).toString()
                                                }
                                            }
                                        }
                                    })
                                SetYoYo(Techniques.Swing, 500, btnFollow)
                                model.iFollowed = "0"
                            }
                        }
                    } else {
                        btnFollow.visibility = View.GONE
                    }
                })
            }

            override fun onFailure() {
                requireActivity().runOnUiThread(Runnable {
                    Toast.makeText(requireContext(), "خطا در ارتباط با سرور!", Toast.LENGTH_LONG)
                        .show()
                })
            }
        })
    }
}