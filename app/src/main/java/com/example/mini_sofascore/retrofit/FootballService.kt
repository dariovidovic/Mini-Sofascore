package com.example.mini_sofascore.retrofit

import android.media.Image
import com.example.mini_sofascore.data.Matches
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballService {

    @GET("/sport/{slug}/events/{date}")
    suspend fun getMatches(@Path("slug") slug: String, @Path("date") date: String) : Response<List<Matches?>>

}