package com.example.mini_sofascore.utils

import com.example.mini_sofascore.R
import java.util.*

object Helper {

    fun getTeamImageUrl(id: Int?): String {
        return "https://academy.dev.sofascore.com/team/$id/image"
    }

    fun getTournamentImageUrl(id: Int?): String {
        return "https://academy.dev.sofascore.com/tournament/$id/image"
    }

    fun getPlayerImageUrl(id: Int?): String {
        return "https://academy.dev.sofascore.com/player/$id/image"
    }

    fun getCountryImageUrl(countryCode: String): String {
        return "https://flagsapi.com/$countryCode/flat/64.png"
    }

    fun getCountryCode(countryName: String): String {
        val isoCountryCodes = Locale.getISOCountries()
        for (code in isoCountryCodes) {
            val locale = Locale("", code)
            if (countryName == "USA")
                return "US"
            else if (countryName == "England" || countryName == "Scotland" || countryName == "Wales") {
                return "GB"
            }
            if (countryName.equals(locale.displayCountry, ignoreCase = true)) {
                return code
            }
        }
        return "HR"
    }


}