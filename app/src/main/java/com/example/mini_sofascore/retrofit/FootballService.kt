package com.example.mini_sofascore.retrofit

import com.example.mini_sofascore.data.*
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
    suspend fun getTournaments(@Path("slug") slug: String): Response<List<Tournament?>>

    @GET("/event/{id}")
    suspend fun getEvent(@Path("id") id: Int) : Response<Event>

    @GET("/event/{id}/incidents")
    suspend fun getIncidents(@Path("id") id: Int) : Response<List<Incidents?>>

    @GET("/tournament/{id}")
    suspend fun getTournamentById(@Path("id") id: Int) : Response<Tournament>

    @GET("/tournament/{id}/standings")
    suspend fun getTournamentStandings(@Path("id") id: Int) : Response<List<StandingsResponse?>>
}