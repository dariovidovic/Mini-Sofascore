package com.example.mini_sofascore.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {
        private const val baseUrl = "https://academy.dev.sofascore.com"
        private var instance: FootballService? = null
        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val okHttpClient = OkHttpClient.Builder().addInterceptor(logger)

        @Synchronized
        fun getRetroInstance(): FootballService {
            if (instance == null)
                instance = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .baseUrl(baseUrl)
                    .build()
                    .create(FootballService::class.java)
            return instance as FootballService
        }
    }
}