package com.example.tryingsubmission.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tryingsubmission.data.remote.response.DetailUserResponse
import com.example.tryingsubmission.data.remote.response.GithubResponse
import com.example.tryingsubmission.data.remote.response.ItemsItem
import com.example.tryingsubmission.data.local.FavoriteDao
import com.example.tryingsubmission.data.local.entity.FavoriteEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.tryingsubmission.data.remote.retrofit.ApiService
import java.util.concurrent.ExecutorService


class GithubUserRepository private constructor(
    private val apiService: ApiService,
    private val executorService: ExecutorService,
    private val favoriteDao: FavoriteDao
) {

    fun insert(favoriteEntity: FavoriteEntity){
        executorService.execute{
            favoriteDao.insert(favoriteEntity)
        }
    }

    fun delete(favoriteEntity: FavoriteEntity){
        executorService.execute {
            favoriteDao.delete(favoriteEntity)
        }
    }


    fun getAllListFavorite():LiveData<List<FavoriteEntity>> = favoriteDao.getAllListFavoriteUser()

    fun getDataFavorite(favoriteEntity: FavoriteEntity):LiveData<FavoriteEntity> = favoriteDao.getDataFavoriteByUsername(favoriteEntity.login)



    private fun <T> makeApiCall(apiCall: Call<T>): LiveData<Result<T>> {
        val result = MutableLiveData<Result<T>>()
        result.value = Result.Loading
        apiCall.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        result.value = Result.Success(items)
                    } else {
                        result.value = Result.Error("Data not found")
                    }
                } else {
                    result.value = Result.Error("Error ${response.code()}")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                result.value = Result.Error(t.message ?: "Unknown error")
            }
        })

        return result
    }

    fun dataGithubUserList(user : String) : LiveData<Result<GithubResponse>> {
        return makeApiCall(apiService.getGitUser(user))
    }
    fun dataDetailGithubUser(user: String):LiveData<Result<DetailUserResponse>>{
        return makeApiCall(apiService.getDetailUser(user))
    }
    fun dataFollowersGithubUser(user : String):LiveData<Result<List<ItemsItem>>>{
        return makeApiCall(apiService.getFollowers(user))
    }
    fun dataFollowingGithubUser(user : String):LiveData<Result<List<ItemsItem>>>{
        return makeApiCall(apiService.getFollowing(user))
    }



    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            executorService: ExecutorService,
            favoriteDao: FavoriteDao
        ): GithubUserRepository = instance ?: synchronized(this) {
            instance ?: GithubUserRepository(apiService,executorService,favoriteDao)
        }.also { instance = it }
    }

}

