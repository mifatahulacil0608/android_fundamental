package com.example.tryingsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tryingsubmission.data.local.entity.FavoriteEntity
import com.example.tryingsubmission.data.remote.GithubUserRepository

class FavoriteViewModel(private val githubUserRepository: GithubUserRepository):ViewModel() {
    fun getAllListFavorite():LiveData<List<FavoriteEntity>> = githubUserRepository.getAllListFavorite()
}