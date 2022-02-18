package com.example.shahingram.Home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shahingram.HideOrShowBottomNav
import com.example.shahingram.MainActivity
import com.example.shahingram.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class Fragment_Home() : Fragment() {
    private val viewModel = Home_ViewModel.STon.instance
    private lateinit var myView: View
    private lateinit var prefInfo: SharedPreferences
    private var userId = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        if (MainActivity.currentFrag == "Splash" || MainActivity.currentFrag == "Story" || MainActivity.currentFrag == "Comments")
            HideOrShowBottomNav("Show")
        myView = inflater.inflate(R.layout.fragment_home, container, false)
        prefInfo = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        userId = prefInfo.getString("userId", "")!!
        GetStories()
        GetPosts()

        return myView
    }

    private fun GetStories() {
        viewModel.getStories(object : Home_ViewModel.OnGetStories {
            override fun onReceived(stories: List<Story>) {
                requireActivity().runOnUiThread(Runnable {
                    myView.rv_mainFragment_stories.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    myView.rv_mainFragment_stories.adapter = Rv_Stories_Adapter(requireContext(),
                        stories,
                        object : Rv_Stories_Adapter.OnStoryClick {
                            override fun onClick(view: View, story: Story) {
                                val nextAction =
                                    Fragment_HomeDirections.actionFragmentHomeToFragmentStory(story)
                                Navigation.findNavController(view).navigate(nextAction)
                            }
                        })
                })
            }

            override fun onFailure() {
                requireActivity().runOnUiThread(Runnable {
                    Toast.makeText(requireContext(),
                        R.string.connection_error,
                        Toast.LENGTH_SHORT).show()
                })
            }
        })
    }

    private fun GetPosts() {
        viewModel.getPosts(userId, object : Home_ViewModel.OnGetPostReceived {
            override fun onReceived(models: List<Post>) {
                requireActivity().runOnUiThread(Runnable {
                    myView.rv_mainFragment_posts.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    myView.rv_mainFragment_posts.adapter =
                        Rv_Posts_Adapter(requireActivity(),
                            models,
                            object : Rv_Posts_Adapter.OnCommentClick {
                                override fun onClick(
                                    view: View,
                                    myComment: MyCommentModel,
                                ) {
                                    val nextAction =
                                        Fragment_HomeDirections.actionFragmentHomeToFragmentComments(
                                            myComment)
                                    Navigation.findNavController(view).navigate(nextAction)
                                }
                            } , object : Rv_Posts_Adapter.OnPostsUpdate{
                                override fun onUpdate() {
                                    requireActivity().runOnUiThread {
                                        GetPosts()
                                    }
                                }
                            })
                })
            }

            override fun onFailure(message: String) {
                Log.i("LOG", "onFailure: $message")
                requireActivity().runOnUiThread(Runnable {
                    Toast.makeText(context, "خطا در ارتباط با سرور", Toast.LENGTH_SHORT).show()
                })
            }
        })
    }
}