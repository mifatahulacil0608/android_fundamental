package com.example.tryingsubmission.ui.viewmodel


import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import com.example.tryingsubmission.data.remote.response.GithubResponse
import com.example.tryingsubmission.data.remote.GithubUserRepository
import com.example.tryingsubmission.data.remote.Result

class MainViewModel(private val githubUserRepository: GithubUserRepository):ViewModel() {
    lateinit var githubUser : LiveData<Result<GithubResponse>>

    init {
        getSearchDataUser("arif")
    }

    fun getSearchDataUser(username : String){
        githubUser = githubUserRepository.dataGithubUserList(username)
    }

}