package com.example.mini_sofascore.retrofit

import com.example.mini_sofascore.data.Event
import com.example.mini_sofascore.data.Incidents
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.data.Tournaments
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballService {

    @GET("/sport/{slug}/events/{date}")
    suspend fun getMatches(
        @Path("slug") slug: String,
        @Path("date") date: String
    ): Response<List<Match?>>

    @GET("/sport/{slug}/tournaments")
    suspend fun getTournaments(@Path("slug") slug: String): Response<List<Tournaments?>>

    @GET("/event/{id}")
    suspend fun getEvent(@Path("id") id: Int) : Response<Event>

    @GET("/event/{id}/incidents")
    suspend fun getIncidents(@Path("id") id: Int) : Response<List<Incidents?>>
}