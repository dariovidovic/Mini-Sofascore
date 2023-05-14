package com.example.mini_sofascore.data

data class StandingsResponse(
    val id: Int,
    val tournament: Tournament,
    val type: String,
    val sortedStandingsRows: List<TeamStanding>
)

data class TeamStanding(
    val id: Int,
    val team: Team,
    val points: Int,
    val scoresFor: Int,
    val scoresAgainst: Int,
    val played: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int
)

data class Team(val id: Int, val name: String)
