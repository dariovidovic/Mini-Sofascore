@file:Suppress("DEPRECATION")

package com.example.mini_sofascore

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mini_sofascore.databinding.ActivitySettingsBinding
import com.example.mini_sofascore.utils.Slug
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    var firstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences(Slug.SETTINGS, MODE_PRIVATE)
        binding.appLanguage.text =
            sharedPreferences.getString(Slug.LANGUAGE, resources.getString(R.string.english))
        val languages = arrayOf(
            "",
            resources.getString(R.string.english),
            resources.getString(R.string.croatian)
        )
        val languagesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = languagesAdapter



        val appMode: Int = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (appMode) {
            Configuration.UI_MODE_NIGHT_YES -> binding.darkButton.isChecked = true
            Configuration.UI_MODE_NIGHT_NO -> binding.lightButton.isChecked = true
        }

        binding.darkButton.setOnClickListener {
            binding.lightButton.isChecked = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }
        binding.lightButton.setOnClickListener {
            binding.darkButton.isChecked = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }

        binding.backButtonLayout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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
                            resources.getString(R.string.english) -> {
                                setLocale(this@SettingsActivity, Locale.getDefault().language)
                                savePreferences(
                                    Slug.LANGUAGE,
                                    resources.getString(R.string.english)
                                )
                            }
                            resources.getString(R.string.croatian) -> {
                                setLocale(this@SettingsActivity, "hr")
                                savePreferences(
                                    Slug.LANGUAGE,
                                    resources.getString(R.string.croatian)
                                )
                            }
                        }
                    }
                    firstLoad = false

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }


    }

    private fun savePreferences(key: String, value: String) {
        val sharedPreferences = getSharedPreferences(Slug.SETTINGS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
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