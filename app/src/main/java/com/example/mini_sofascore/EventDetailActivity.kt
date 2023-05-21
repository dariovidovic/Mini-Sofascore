package com.example.mini_sofascore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.mini_sofascore.adapters.IncidentsAdapter
import com.example.mini_sofascore.databinding.ActivityEventDetailBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.viewmodels.EventViewModel
import com.example.mini_sofascore.viewmodels.IncidentsViewModel


private lateinit var eventViewModel: EventViewModel
private lateinit var incidentsViewModel: IncidentsViewModel

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentMatchId = intent.extras?.getInt(EVENT_ID)

        eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]
        incidentsViewModel = ViewModelProvider(this)[IncidentsViewModel::class.java]

        eventViewModel.getEventById(currentMatchId ?: 0)
        incidentsViewModel.getIncidentsById(currentMatchId ?: 0)

        Log.d("TEST", currentMatchId.toString())

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

                homeTeamLogo.setOnClickListener {
                    TeamDetailsActivity.start(this@EventDetailActivity, eventViewModel.event.value?.homeTeam?.id?:1)
                }

                awayTeamLogo.setOnClickListener {
                    TeamDetailsActivity.start(this@EventDetailActivity, eventViewModel.event.value?.awayTeam?.id?:1)
                }

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

        val adapter = IncidentsAdapter()
        val linearLayoutManager =
            LinearLayoutManager(this@EventDetailActivity, LinearLayoutManager.VERTICAL, false)
        binding.matchDetailsRecyclerView.adapter = adapter
        binding.matchDetailsRecyclerView.layoutManager = linearLayoutManager
        incidentsViewModel.incidents.observe(this) {
            adapter.setData(it.reversed(), eventViewModel.event.value?.tournament?.sport?.name ?: "football")
        }



        binding.backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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

