package com.example.shahingram.Profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.example.shahingram.HideOrShowBottomNav
import com.example.shahingram.Home.Fragment_HomeDirections
import com.example.shahingram.Home.MyCommentModel
import com.example.shahingram.Home.Post
import com.example.shahingram.Home.Rv_Posts_Adapter
import com.example.shahingram.MainActivity
import com.example.shahingram.R
import com.example.shahingram.SetYoYo
import kotlinx.android.synthetic.main.fragment_comments.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_saved.view.*

class Fragment_Saved : Fragment() {
    private lateinit var mView: View
    private lateinit var prefInfo: SharedPreferences
    private var userId = ""
    private val viewModel = Profile_ViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mView = inflater.inflate(R.layout.fragment_saved, container, false)

        SetupViews()

        return mView
    }

    private fun SetupViews() {
        HideOrShowBottomNav("Hide")
        MainActivity.currentFrag = "Saved"

        prefInfo = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        if (prefInfo.getString("username", "")!!.isNotEmpty()) {
            userId = prefInfo.getString("userId", "")!!
        }
        mView.rv_savedFragment_posts.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        GetPosts(userId)

        mView.img_savedFragment_back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun GetPosts(userId: String) {
        viewModel.getSavedPosts(userId, object : Profile_ViewModel.OnSavedPostsReceived {
            override fun onReceived(posts: List<Post>) {
                requireActivity().runOnUiThread {
                    if (posts[0].id!!.isEmpty()){
                        Toast.makeText(requireContext(),"Saved Is Empty!",Toast.LENGTH_SHORT).show()
                        SetYoYo(Techniques.Shake,1500,mView.txt_savedFragment_empty)
                        mView.txt_savedFragment_empty.visibility = View.VISIBLE
                    }else{
                        mView.rv_savedFragment_posts.adapter = Rv_Posts_Adapter(requireActivity(),
                            posts,
                            object : Rv_Posts_Adapter.OnCommentClick {
                                override fun onClick(view: View, myComment: MyCommentModel) {
                                    val nextAction =
                                        Fragment_SavedDirections.actionFragmentSavedToFragmentComments(
                                            myComment)
                                    Navigation.findNavController(view).navigate(nextAction)
                                }

                            }, object : Rv_Posts_Adapter.OnPostsUpdate {
                                override fun onUpdate() {
                                    requireActivity().runOnUiThread {
                                        Toast.makeText(requireContext(),"Update",Toast.LENGTH_SHORT).show()
                                        GetPosts(userId)
                                    }
                                }
                            })
                    }
                }
            }
        })
    }
}