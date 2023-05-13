package com.example.mini_sofascore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mini_sofascore.adapters.MatchesViewPagerAdapter
import com.example.mini_sofascore.databinding.ActivityEventDetailBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.viewmodels.EventViewModel

private lateinit var binding: ActivityEventDetailBinding
private lateinit var eventViewModel: EventViewModel

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentMatchId = intent.extras?.getInt(EVENT_ID)
        Log.d("TEST", currentMatchId.toString())
        eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]

        eventViewModel.getEventById(currentMatchId ?: 0)

        eventViewModel.event.observe(this) {
            binding.run {
                eventInfo.text = this@EventDetailActivity.getString(
                    R.string.event_info,
                    eventViewModel.event.value?.tournament?.sport?.name,
                    eventViewModel.event.value?.tournament?.country?.name,
                    eventViewModel.event.value?.tournament?.name,
                    eventViewModel.event.value?.round.toString()
                )

                tournamentLogo.load(Helper.getTournamentImageUrl(eventViewModel.event.value?.tournament?.id))
                homeTeamLogo.load(Helper.getTeamImageUrl(eventViewModel.event.value?.homeTeam?.id))
                awayTeamLogo.load(Helper.getTeamImageUrl(eventViewModel.event.value?.awayTeam?.id))
                homeTeamName.text = eventViewModel.event.value?.homeTeam?.name
                awayTeamName.text = eventViewModel.event.value?.awayTeam?.name
                homeTeamScore.text = eventViewModel.event.value?.homeScore?.total.toString()
                awayTeamScore.text = eventViewModel.event.value?.awayScore?.total.toString()

                val event = eventViewModel.event.value
                if (event?.status == "finished") {
                    eventStatus.text = "Full Time"
                } else eventStatus.text = "18:00"


            }
        }




        binding.backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }

    companion object {
        private const val EVENT_ID = "event_id"

        fun start(context: Context, id: Int) {
            Intent(context, EventDetailActivity::class.java).apply {
                putExtra(EVENT_ID, id)
            }.also {
                context.startActivity(it)
            }

        }

    }
}

