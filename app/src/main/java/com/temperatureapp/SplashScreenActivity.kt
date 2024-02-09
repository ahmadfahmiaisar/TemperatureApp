package com.temperatureapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.temperatureapp.Config.NAME_FILE_SHARED_PREFERENCE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        checkSharedPreference()
    }

    private fun checkSharedPreference() {
        val preference = getSharedPreferences(NAME_FILE_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val username =
            preference.getString(Config.VALUE_PREF_USERNAME, getString(R.string.username))
        val password =
            preference.getString(Config.VALUE_PREF_PASSWORD, getString(R.string.password))
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            lifecycleScope.launch {
                delay(500)
                startActivity(Intent(this@SplashScreenActivity, RegisterActivity::class.java))
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}