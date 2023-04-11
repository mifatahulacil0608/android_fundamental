package com.example.tryingsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tryingsubmission.data.remote.response.DetailUserResponse
import com.example.tryingsubmission.data.local.entity.FavoriteEntity
import com.example.tryingsubmission.data.remote.GithubUserRepository
import com.example.tryingsubmission.data.remote.Result
import kotlinx.coroutines.launch

class DetailUserViewModel(private val githubUserRepository: GithubUserRepository):ViewModel() {
    lateinit var githubUserDetail : LiveData<Result<DetailUserResponse>>
    lateinit var favoriteUserByUsername : LiveData<FavoriteEntity>

fun getDetailGithubUser(username : String){
    githubUserDetail = githubUserRepository.dataDetailGithubUser(username)
}
    fun insertFavorite(favoriteEntity: FavoriteEntity){
        viewModelScope.launch {
            githubUserRepository.insert(favoriteEntity)
        }

    }

    fun deleteFavorite(favoriteEntity: FavoriteEntity){
        viewModelScope.launch {
            githubUserRepository.delete(favoriteEntity)
        }

    }



    fun getDataFavorite(favorite:FavoriteEntity){
        favoriteUserByUsername = githubUserRepository.getDataFavorite(favorite)
    }
}