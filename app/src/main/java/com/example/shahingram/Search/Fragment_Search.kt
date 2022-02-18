package com.example.shahingram.Search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shahingram.HideOrShowBottomNav
import com.example.shahingram.Home.Post
import com.example.shahingram.Like.Like_ViewModel
import com.example.shahingram.MainActivity
import com.example.shahingram.Profile.UserProfPrev
import com.example.shahingram.R
import com.example.shahingram.SpannedGridLayoutManager
import com.example.shahingram.SpannedGridLayoutManager.SpanInfo
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class Fragment_Search : Fragment() {
    private lateinit var prefInfo: SharedPreferences
    private lateinit var mView: View
    private lateinit var rvPosts: RecyclerView
    private lateinit var rvUsers: RecyclerView
    private lateinit var edtSearch: EditText
    private var userId = ""
    private val viewModel = Search_ViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        if (MainActivity.currentFrag == "Posts" || MainActivity.currentFrag == "UserProfile")
            HideOrShowBottomNav("Show")
        mView = inflater.inflate(R.layout.fragment_search, container, false)

        SetupViews()

        return mView;
    }

    override fun onResume() {
        if (edtSearch.text.toString().isNotEmpty()) {
            rvUsers.visibility = View.VISIBLE
            rvPosts.visibility = View.GONE
            SearchUser(edtSearch.text.toString())
        } else {
            rvUsers.visibility = View.GONE
            rvPosts.visibility = View.VISIBLE
        }
        super.onResume()
    }

    private fun SetupViews() {
        prefInfo = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        if (prefInfo.getString("userId", "")!!.isNotEmpty())
            userId = prefInfo.getString("userId", "")!!

        rvPosts = mView.findViewById(R.id.rv_searchFragment_posts)
        rvUsers = mView.findViewById(R.id.rv_searchFragment_users)
        edtSearch = mView.findViewById(R.id.edt_searchFragment_search)

        GetPosts()

        mView.edt_searchFragment_search.doAfterTextChanged {
            //HideOrShowBottomNav("Hide")
            val username = mView.edt_searchFragment_search.text.toString()
            SearchUser(username)
        }
    }

    private fun SearchUser(username: String) {
        if (username.isNotEmpty()) {
            viewModel.searchUser(username, object : Search_ViewModel.OnSearchUserReceived {
                override fun onReceived(users: List<UserProfPrev>) {
                    requireActivity().runOnUiThread {
                        if (users[0].username!!.isNotEmpty()){
                            mView.txt_searchFragment_notFound.visibility = View.GONE
                            rvPosts.visibility = View.GONE
                            rvUsers.visibility = View.VISIBLE
                            rvUsers.layoutManager = LinearLayoutManager(requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false)
                            rvUsers.adapter = Rv_UserSearch_Adapter(requireContext(), users)
                        }else{
                            mView.txt_searchFragment_notFound.visibility = View.VISIBLE
                            rvUsers.visibility = View.GONE
                        }
                    }
                }
            })
        } else {
            GetPosts()
            mView.txt_searchFragment_notFound.visibility = View.GONE
            rvUsers.visibility = View.GONE
            rvPosts.visibility = View.VISIBLE
        }
    }

    private fun GetPosts() {
        val manager = SpannedGridLayoutManager(
            { position ->
                if (position % 6 == 0 || position % 6 == 4) {
                    SpanInfo(2, 2)
                } else {
                    SpanInfo(1, 1)
                }
            },
            3,
            1f
        )

        rvPosts.layoutManager = manager
        viewModel.getPosts(userId, object : Search_ViewModel.OnPostsReceived {
            override fun onReceived(posts: List<Post>) {
                requireActivity().runOnUiThread(Runnable {
                    rvPosts.adapter = Rv_PostsSearch_Adapter(
                        requireContext(),
                        posts,
                        object : Rv_PostsSearch_Adapter.OnPostClick {
                            override fun onClick(view: View, post: Post) {
                                val nextAction =
                                    Fragment_SearchDirections.actionFragmentSearchToFragmentPosts(
                                        post)
                                Navigation.findNavController(view).navigate(nextAction)
                            }
                        })
                })
            }
            override fun onFailure(message: String) {
                Log.i("LOG", "onFailure: $message")
            }
        })
    }
}