package com.example.mini_sofascore.data

data class Incidents(
    val text: String,
    val player: Player,
    val scoringTeam: String,
    val homeScore: Int,
    val awayScore: Int,
    val goalType: String,
    val teamSide: String,
    val color: String,
    val time: Int,
    val type: String
)

data class Player(
    val id: Int,
    val name: String
)