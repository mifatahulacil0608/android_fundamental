package com.example.tryingsubmission.ui.factoryviewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.tryingsubmission.data.remote.GithubUserRepository
import com.example.tryingsubmission.di.Injection
import com.example.tryingsubmission.ui.viewmodel.DetailUserViewModel
import com.example.tryingsubmission.ui.viewmodel.FavoriteViewModel
import com.example.tryingsubmission.ui.viewmodel.FollowViewModel
import com.example.tryingsubmission.ui.viewmodel.MainViewModel

class ViewModelFactory private constructor(private val githubUserRepository: GithubUserRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(githubUserRepository) as T
        }
        if(modelClass.isAssignableFrom(DetailUserViewModel::class.java)){
            return DetailUserViewModel(githubUserRepository) as T
        }
        if (modelClass.isAssignableFrom(FollowViewModel::class.java)){
            return FollowViewModel(githubUserRepository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(githubUserRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class: "+modelClass.name)
    }

    companion object{
        @Volatile
        private var instance : ViewModelFactory?=null
        fun getInstance(context : Context) : ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}