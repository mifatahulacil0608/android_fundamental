@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection")

package com.example.tryingsubmission.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("SpellCheckingInspection", "SpellCheckingInspection")
class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .header("Authorization", "token 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")
                    //.addHeader("Authorization","token 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")
                    .build()
                chain.proceed(requestHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                //.addInterceptor(loggingInterceptor)

                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}