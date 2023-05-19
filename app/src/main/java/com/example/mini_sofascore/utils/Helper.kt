package com.example.mini_sofascore.utils

object Helper {

    fun getTeamImageUrl(id: Int?): String {
        return "https://academy.dev.sofascore.com/team/$id/image"
    }

    fun getTournamentImageUrl(id: Int?) : String {
        return "https://academy.dev.sofascore.com/tournament/$id/image"
    }



}