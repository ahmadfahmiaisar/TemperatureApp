package com.smadu1.temperatureapp.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.smadu1.temperatureapp.R
import com.smadu1.temperatureapp.utils.Config.NAME_FILE_SHARED_PREFERENCE
import com.smadu1.temperatureapp.databinding.ActivityStarterScreenBinding
import com.smadu1.temperatureapp.main.MainActivity
import com.smadu1.temperatureapp.utils.Config
import com.smadu1.temperatureapp.utils.startActivityWithAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StarterScreenActivity : AppCompatActivity() {
    private var _binding: ActivityStarterScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStarterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSharedPreference()
    }

    private fun checkSharedPreference() {
        val preference = getSharedPreferences(NAME_FILE_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val username = preference.getString(Config.VALUE_PREF_USERNAME, null)
        val password = preference.getString(Config.VALUE_PREF_PASSWORD, null)

        val isNotRegistered = username.isNullOrEmpty() || password.isNullOrEmpty()

        lifecycleScope.launch {
            delay(1000)
            val intent = Intent(
                this@StarterScreenActivity,
                if (isNotRegistered) RegisterActivity::class.java else LoginActivity::class.java
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivityWithAnimation(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}