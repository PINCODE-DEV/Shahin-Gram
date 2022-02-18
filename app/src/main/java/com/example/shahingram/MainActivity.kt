package com.example.shahingram

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.example.shahingram.AddPost.Fragment_AddPostDirections
import com.example.shahingram.Home.*
import com.fxn.BubbleTabBar
import com.fxn.OnBubbleClickListener
import com.example.shahingram.Like.Fragment_LikeDirections
import com.example.shahingram.Profile.Fragment_ProfileDirections
import com.example.shahingram.Profile.UserProfPrev
import com.example.shahingram.Search.Fragment_SearchDirections


class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var bottomNav : BubbleTabBar
        lateinit var currentFrag : String
    }
    private lateinit var nextAction: NavDirections

    private val HOME_FRAGMENT_ID = 2131296495
    private val SEARCH_FRAGMENT_ID = 2131296500
    private val ADDPOST_FRAGMENT_ID = 2131296493
    private val LIKE_FRAGMENT_ID = 2131296496
    private val PROFILE_FRAGMENT_ID = 2131296498

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SetStatusBarColor(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        SetupViews()

    }

    private fun SetupViews() {
        currentFrag = "Home"
        bottomNav = findViewById(R.id.main_bottomNav)

        bottomNav.addBubbleListener(OnBubbleClickListener { i ->
            when (i) {
                2131296572 -> {
                    currentFrag = "Home"
                    when (navController.currentDestination?.id) {
                        SEARCH_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_SearchDirections.actionFragmentSearchToFragmentHome()
                            navController.navigate(nextAction)
                        }
                        ADDPOST_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_AddPostDirections.actionFragmentAddPostToFragmentHome()
                            navController.navigate(nextAction)
                        }
                        LIKE_FRAGMENT_ID -> {
                            nextAction = Fragment_LikeDirections.actionFragmentLikeToFragmentHome()
                            navController.navigate(nextAction)
                        }
                        PROFILE_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_ProfileDirections.actionFragmentProfileToFragmentHome()
                            navController.navigate(nextAction)
                        }
                    }
                }
                2131296575 -> {
                    currentFrag = "Search"
                    when (navController.currentDestination?.id) {
                        HOME_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_HomeDirections.actionFragmentHomeToFragmentSearch()
                            navController.navigate(nextAction)
                        }
                        ADDPOST_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_AddPostDirections.actionFragmentAddPostToFragmentSearch()
                            navController.navigate(nextAction)
                        }
                        LIKE_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_LikeDirections.actionFragmentLikeToFragmentSearch()
                            navController.navigate(nextAction)
                        }
                        PROFILE_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_ProfileDirections.actionFragmentProfileToFragmentSearch()
                            navController.navigate(nextAction)
                        }
                    }
                }
                2131296571 -> {
                    currentFrag = "AddPost"
                    when (navController.currentDestination?.id) {
                        HOME_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_HomeDirections.actionFragmentHomeToFragmentAddPost()
                            navController.navigate(nextAction)
                        }
                        SEARCH_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_SearchDirections.actionFragmentSearchToFragmentAddPost()
                            navController.navigate(nextAction)
                        }
                        LIKE_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_LikeDirections.actionFragmentLikeToFragmentAddPost()
                            navController.navigate(nextAction)
                        }
                        PROFILE_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_ProfileDirections.actionFragmentProfileToFragmentAddPost()
                            navController.navigate(nextAction)
                        }
                    }
                }
                2131296573 -> {
                    currentFrag = "Like"
                    when (navController.currentDestination?.id) {
                        HOME_FRAGMENT_ID -> {
                            nextAction = Fragment_HomeDirections.actionFragmentHomeToFragmentLike()
                            navController.navigate(nextAction)
                        }
                        SEARCH_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_SearchDirections.actionFragmentSearchToFragmentLike()
                            navController.navigate(nextAction)
                        }
                        ADDPOST_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_AddPostDirections.actionFragmentAddPostToFragmentLike()
                            navController.navigate(nextAction)
                        }
                        PROFILE_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_ProfileDirections.actionFragmentProfileToFragmentLike()
                            navController.navigate(nextAction)
                        }
                    }
                }
                2131296574 -> {
                    currentFrag = "Profile"
                    val prof = UserProfPrev("me","me","me")
                    when (navController.currentDestination?.id) {
                        HOME_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_HomeDirections.actionFragmentHomeToFragmentProfile(prof)
                            navController.navigate(nextAction)
                        }
                        SEARCH_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_SearchDirections.actionFragmentSearchToFragmentProfile(prof)
                            navController.navigate(nextAction)
                        }
                        ADDPOST_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_AddPostDirections.actionFragmentAddPostToFragmentProfile(prof)
                            navController.navigate(nextAction)
                        }
                        LIKE_FRAGMENT_ID -> {
                            nextAction =
                                Fragment_LikeDirections.actionFragmentLikeToFragmentProfile(prof)
                            navController.navigate(nextAction)
                        }
                    }
                }
            }
        })
    }
}