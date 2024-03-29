package com.smadu1.temperatureapp.onboarding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.smadu1.temperatureapp.utils.Config
import com.smadu1.temperatureapp.utils.Config.NAME_FILE_SHARED_PREFERENCE
import com.smadu1.temperatureapp.main.MainActivity
import com.smadu1.temperatureapp.R
import com.smadu1.temperatureapp.databinding.ActivityLoginBinding
import com.smadu1.temperatureapp.utils.startActivityWithAnimation

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        binding.btnSubmit.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "mohon isi dahulu", Toast.LENGTH_SHORT).show()
            } else {
                checkValueSharedPreference(username, password)
            }
        }
    }

    private fun checkValueSharedPreference(username: String, password: String) {
        val pref = getSharedPreferences(NAME_FILE_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val savedUsername = pref.getString(Config.VALUE_PREF_USERNAME, getString(R.string.username))
        val savedPassword = pref.getString(Config.VALUE_PREF_USERNAME, getString(R.string.password))

        if (username.contentEquals(savedUsername) || password.contentEquals(savedPassword)) {
            val intent = Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivityWithAnimation(intent)
        } else {
            Toast.makeText(this, "username atau password tidak ditemukan", Toast.LENGTH_SHORT)
                .show()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}