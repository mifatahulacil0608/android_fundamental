package com.example.tryingsubmission.di

import android.content.Context
import com.example.tryingsubmission.data.local.FavoriteDatabase
import com.example.tryingsubmission.data.remote.retrofit.ApiConfig
import com.example.tryingsubmission.data.remote.GithubUserRepository
import java.util.concurrent.Executors

object Injection {

    fun provideRepository(context: Context): GithubUserRepository {
        val apiService = ApiConfig.getApiService()
        val dataBase = FavoriteDatabase.getInstance(context)
        val dao = dataBase.favoriteDao()
        val executorService = Executors.newSingleThreadExecutor()
        return GithubUserRepository.getInstance(apiService,executorService,dao)
    }

}