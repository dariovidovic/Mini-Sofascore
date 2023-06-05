@file:Suppress("DEPRECATION")

package com.example.mini_sofascore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_sofascore.databinding.ActivitySettingsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    var firstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MiniSofascore)
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val languages = arrayOf("English", "Croatian")
        val languagesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = languagesAdapter

        binding.darkButton.setOnClickListener {
            binding.lightButton.isChecked = false
        }
        binding.lightButton.setOnClickListener {
            binding.darkButton.isChecked = false
        }

        binding.logOutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (!firstLoad) {
                        when (p0?.selectedItem) {
                            "English" -> setLocale(this@SettingsActivity, "en")
                            "Croatian" -> setLocale(this@SettingsActivity, "hr")
                        }
                    }
                    firstLoad = false

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }


    }

    fun setLocale(activity: Activity, selectedLanguageCode: String) {
        val locale = Locale(selectedLanguageCode)
        Locale.setDefault(locale)

        val resources = activity.resources

        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}