package com.smadu1.temperatureapp.onboarding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.smadu1.temperatureapp.utils.Config.NAME_FILE_SHARED_PREFERENCE
import com.smadu1.temperatureapp.utils.Config.VALUE_PREF_ADDRESS
import com.smadu1.temperatureapp.utils.Config.VALUE_PREF_FULL_NAME
import com.smadu1.temperatureapp.utils.Config.VALUE_PREF_PASSWORD
import com.smadu1.temperatureapp.utils.Config.VALUE_PREF_USERNAME
import com.smadu1.temperatureapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        binding.btnSubmit.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val address = binding.etAlamat.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (fullName.isEmpty() || address.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "isi dahulu datanya", Toast.LENGTH_SHORT).show()
            } else {
                saveToSharedPreference(fullName, address, username, password)
            }
        }
    }

    private fun saveToSharedPreference(
        fullName: String,
        address: String,
        username: String,
        password: String
    ) {
        try {
            val pref = getSharedPreferences(NAME_FILE_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            val editPref = pref.edit()
            editPref.putString(VALUE_PREF_FULL_NAME, fullName)
            editPref.putString(VALUE_PREF_ADDRESS, address)
            editPref.putString(VALUE_PREF_USERNAME, username)
            editPref.putString(VALUE_PREF_PASSWORD, password)
            editPref.apply()

            lifecycleScope.launch {
                Toast.makeText(this@RegisterActivity, "tunggu sebentar", Toast.LENGTH_SHORT).show()
                delay(500)

                val valueUsername = pref.getString(VALUE_PREF_USERNAME, "")
                if (valueUsername.isNullOrEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "register gagal, silahkan coba lagi",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "tidak berhasil menyimpan, silahkan coba lagi", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}