package com.example.shahingram.Like

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shahingram.Profile.UserProfPrev
import com.example.shahingram.R
import kotlinx.android.synthetic.main.fragment_like.view.*

class Fragment_Like : Fragment() {
    lateinit var mView: View
    private val viewModel = Like_ViewModel.STon.instance
    private lateinit var prefInfo: SharedPreferences
    private var userId = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mView = inflater.inflate(R.layout.fragment_like, container, false)
        prefInfo = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        userId = prefInfo.getString("userId", "")!!

        mView.rv_likeFragment_myFollowers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.getMyFollowers(userId, object : Like_ViewModel.OnMyFollowersReceived {
            override fun onReceived(followers: List<UserProfPrev>) {
                requireActivity().runOnUiThread {
                    if (followers[0].userId!!.isEmpty())
                        Toast.makeText(
                            requireContext(),
                            "کسی هنوز شما را دنبال نمیکند!",
                            Toast.LENGTH_LONG
                        ).show()
                    else
                        mView.rv_likeFragment_myFollowers.adapter =
                            Rv_MyFollowers_Adapter(requireContext(), followers)
                }
            }
        })

        return mView
    }
}