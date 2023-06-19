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
    suspend fun getEvent(@Path("id") id: Int): Response<Event>

    @GET("/event/{id}/incidents")
    suspend fun getIncidents(@Path("id") id: Int): Response<List<Incidents?>>

    @GET("/tournament/{id}")
    suspend fun getTournamentById(@Path("id") id: Int): Response<Tournament>

    @GET("/tournament/{id}/standings")
    suspend fun getTournamentStandings(@Path("id") id: Int): Response<List<StandingsResponse?>>

    @GET("/tournament/{id}/events/{span}/{page}")
    suspend fun getTournamentMatches(
        @Path("id") id: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): Response<List<Match>>

    @GET("/team/{id}/events/{span}/{page}")
    suspend fun getTeamMatches(
        @Path("id") id: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): Response<List<Match>>

    @GET("/player/{id}/events/{span}/{page}")
    suspend fun getPlayerMatches(
        @Path("id") id: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): Response<List<Match>>

    @GET("/player/{id}")
    suspend fun getPlayerDetails(@Path("id") id: Int): Response<Player>

    @GET("/team/{id}/players")
    suspend fun getTeamPlayers(@Path("id") id: Int): Response<List<Player>>

    @GET("/team/{id}")
    suspend fun getTeamDetails(@Path("id") id: Int): Response<Team>

    @GET("/team/{id}/tournaments")
    suspend fun getTeamTournaments(@Path("id") id: Int): Response<List<Tournament>>

    @GET("/event/{id}")
    suspend fun getFavMatch(@Path("id") id: Int): Response<Match>

}