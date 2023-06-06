@file:Suppress("DEPRECATION")

package com.example.mini_sofascore

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.mini_sofascore.adapters.TournamentMatchesAdapter
import com.example.mini_sofascore.databinding.ActivityPlayerDetailsBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.utils.Slug
import com.example.mini_sofascore.utils.Sport
import com.example.mini_sofascore.viewmodels.PlayerViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class PlayerDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDetailsBinding
    private lateinit var viewModel: PlayerViewModel
    private val adapter by lazy { TournamentMatchesAdapter() }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.playerMatchesRecyclerView.adapter = adapter
        binding.playerMatchesRecyclerView.layoutManager = linearLayoutManager

        val currentPlayerId = intent.extras?.getInt(Slug.PLAYER_ID)
        viewModel.playerId = currentPlayerId ?: 1
        viewModel.getPlayerDetails(currentPlayerId ?: 1)
        viewModel.playerMatches.observe(this) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        binding.backIcon.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        viewModel.playerDetails.observe(this) {
            val playerCountryCode = Helper.getCountryCode(it?.country?.name ?: "")
            val countryImageUrl = Helper.getCountryImageUrl(playerCountryCode)

            val birthdayFormat = SimpleDateFormat("dd LLL yyyy")
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date = birthdayFormat.format(parser.parse(it.dateOfBirth) as Date)

            val birthday = birthdayFormat.parse(date) ?: Date()

            val calendar = Calendar.getInstance().time
            val age: Int

            if ((birthday.month < calendar.month)) {
                age = calendar.year - birthday.year
            } else if ((birthday.month > calendar.month)) {
                age = calendar.year - birthday.year - 1
            } else {
                age = if (birthday.date < calendar.date) {
                    calendar.year - birthday.year
                } else if (birthday.date > calendar.date) {
                    calendar.year - birthday.year - 1
                } else {
                    Toast.makeText(
                        this@PlayerDetailsActivity,
                        "Happy Birthday ${it.name}!",
                        Toast.LENGTH_LONG
                    ).show()
                    calendar.year - birthday.year
                }
            }

            binding.run {
                playerName.text = it.name
                playerImage.load(Helper.getPlayerImageUrl(it.id))
                playerTeamName.text = it.team.name
                playerTeamLogo.load(Helper.getTeamImageUrl(it.team.id))
                playerCountryName.text = playerCountryCode
                playerCountryImage.load(countryImageUrl)
                playerPosition.text = it.position
                playerDateOfBirth.text = date
                playerAge.text = resources.getString(
                    R.string.player_age,
                    age
                )
            }
        }

        binding.playerTeamLogo.setOnClickListener {
            TeamDetailsActivity.start(
                this,
                viewModel.playerDetails.value?.team?.id ?: 1,
                viewModel.playerDetails.value?.sport?.name ?: Sport.FOOTBALL
            )
        }

        binding.playerTeamName.setOnClickListener {
            TeamDetailsActivity.start(
                this,
                viewModel.playerDetails.value?.team?.id ?: 1,
                viewModel.playerDetails.value?.sport?.name ?: Sport.FOOTBALL
            )
        }

    }


    companion object {

        fun start(context: Context, id: Int) {
            Intent(context, PlayerDetailsActivity::class.java).apply {
                putExtra(Slug.PLAYER_ID, id)
            }.also {
                context.startActivity(it)
            }

        }
    }
}