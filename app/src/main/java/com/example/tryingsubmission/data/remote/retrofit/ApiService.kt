package com.example.tryingsubmission.data.remote.retrofit
import com.example.tryingsubmission.data.remote.response.GithubResponse
import com.example.tryingsubmission.data.remote.response.ItemsItem
import com.example.tryingsubmission.data.remote.response.DetailUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

@Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection"
)
interface ApiService {

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getGitUser(@Query("q") query: String) : Call<GithubResponse>



    @GET("users/{username}")
    fun getDetailUser(@Path("username") username:String):Call<DetailUserResponse>


    @GET("users/{username}/followers")
    fun getFollowers (@Path("username") username: String): Call<List<ItemsItem>>


    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}