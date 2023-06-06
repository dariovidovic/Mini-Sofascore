package com.example.mini_sofascore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.adapters.EventsAdapter
import com.example.mini_sofascore.databinding.ActivityMyMatchesBinding
import com.example.mini_sofascore.viewmodels.MatchesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MyMatchesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyMatchesBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database =
            FirebaseDatabase.getInstance("https://mini-sofascore-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
        val viewModel = ViewModelProvider(this)[MatchesViewModel::class.java]

        val adapter = EventsAdapter()
        val linearLayoutManager =
            LinearLayoutManager(this@MyMatchesActivity, LinearLayoutManager.VERTICAL, false)

        database.child(auth.currentUser?.uid.toString()).child("favMatches").get()
            .addOnSuccessListener { it ->
                it.children.forEach {
                    viewModel.getFavMatchById(it.value.toString().toInt())
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }

        viewModel.favMatches.observe(this){
                adapter.setData(it)
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        binding.myMatchesRecyclerView.adapter = adapter
        binding.myMatchesRecyclerView.layoutManager = linearLayoutManager

        binding.backButtonLayout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}