package com.temperatureapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.temperatureapp.Config.NAME_FILE_SHARED_PREFERENCE
import com.temperatureapp.databinding.ActivityLoginBinding

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
                checkValueSharedPreference()
            }
        }
    }

    private fun checkValueSharedPreference() {
        val pref = getSharedPreferences(NAME_FILE_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val username = pref.getString(Config.VALUE_PREF_USERNAME, getString(R.string.username))
        val password = pref.getString(Config.VALUE_PREF_USERNAME, getString(R.string.password))

        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "username atau password tidak ditemukan", Toast.LENGTH_SHORT)
                .show()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}