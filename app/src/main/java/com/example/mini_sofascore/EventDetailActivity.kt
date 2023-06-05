package com.example.mini_sofascore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.mini_sofascore.adapters.IncidentsAdapter
import com.example.mini_sofascore.databinding.ActivityEventDetailBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.utils.Slug
import com.example.mini_sofascore.viewmodels.EventViewModel
import com.example.mini_sofascore.viewmodels.IncidentsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


private lateinit var eventViewModel: EventViewModel
private lateinit var incidentsViewModel: IncidentsViewModel

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private var favouriteStatus: Boolean = false
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var favMatchId: Int = 0
    private var currentMatchName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        auth = Firebase.auth
        database =
            FirebaseDatabase.getInstance("https://mini-sofascore-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")

        val currentMatchId = intent.extras?.getInt(Slug.EVENT_ID)
        favMatchId = currentMatchId ?: 0

        eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]
        incidentsViewModel = ViewModelProvider(this)[IncidentsViewModel::class.java]

        eventViewModel.getEventById(currentMatchId ?: 0)
        incidentsViewModel.getIncidentsById(currentMatchId ?: 0)


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

                currentMatchName = eventViewModel.event.value?.slug ?: ""
                database.child(auth.currentUser?.uid.toString()).child("favMatches").get()
                    .addOnSuccessListener {
                        favouriteStatus = it.hasChild(currentMatchName)
                        invalidateOptionsMenu()
                    }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }

                homeTeamLogo.setOnClickListener {
                    TeamDetailsActivity.start(
                        this@EventDetailActivity,
                        eventViewModel.event.value?.homeTeam?.id ?: 1,
                        eventViewModel.event.value?.tournament?.sport?.name ?: ""
                    )
                }

                awayTeamLogo.setOnClickListener {
                    TeamDetailsActivity.start(
                        this@EventDetailActivity,
                        eventViewModel.event.value?.awayTeam?.id ?: 1,
                        eventViewModel.event.value?.tournament?.sport?.name ?: ""
                    )
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
            adapter.setData(
                it.reversed(),
                eventViewModel.event.value?.tournament?.sport?.name ?: "Football"
            )
        }

        binding.backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (!favouriteStatus) {
            menu?.findItem(R.id.action_fav)?.isVisible = true
            menu?.findItem(R.id.action_unfav)?.isVisible = false
        } else {
            menu?.findItem(R.id.action_fav)?.isVisible = false
            menu?.findItem(R.id.action_unfav)?.isVisible = true
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fav -> {
                Toast.makeText(this, "You saved this match!", Toast.LENGTH_SHORT).show()
                database.child(auth.currentUser?.uid.toString())
                    .child("favMatches").child(currentMatchName).setValue(favMatchId)
                favouriteStatus = true
                invalidateOptionsMenu()
            }
            R.id.action_unfav -> {
                Toast.makeText(this, "Removed from favourites", Toast.LENGTH_SHORT).show()
                database.child(auth.currentUser?.uid.toString())
                    .child("favMatches").child(currentMatchName).removeValue()
                favouriteStatus = false
                invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun start(context: Context, id: Int) {
            Intent(context, EventDetailActivity::class.java).apply {
                putExtra(Slug.EVENT_ID, id)
            }.also {
                context.startActivity(it)
            }

        }
    }
}

