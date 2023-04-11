package com.example.tryingsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tryingsubmission.data.remote.response.ItemsItem
import com.example.tryingsubmission.data.remote.Result
import com.example.tryingsubmission.data.remote.GithubUserRepository

class FollowViewModel(private val githubUserRepository: GithubUserRepository):ViewModel() {
    lateinit var followingGithubUser : LiveData<Result<List<ItemsItem>>>
    lateinit var followersGithubUser : LiveData<Result<List<ItemsItem>>>
    fun getFollowersGithubUser(user:String){
        followersGithubUser = githubUserRepository.dataFollowersGithubUser(user)
    }
    fun getFollowingGithubUser(user:String){
        followingGithubUser = githubUserRepository.dataFollowingGithubUser(user)
    }
}