package com.example.mini_sofascore.retrofit

import com.example.mini_sofascore.data.Matches
import com.example.mini_sofascore.data.Tournaments
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballService {

    @GET("/sport/{slug}/events/{date}")
    suspend fun getMatches(@Path("slug") slug: String, @Path("date") date: String) : Response<List<Matches?>>

    @GET("/sport/{slug}/tournaments")
    suspend fun getTournaments(@Path("slug") slug: String) : Response<List<Tournaments?>>

}