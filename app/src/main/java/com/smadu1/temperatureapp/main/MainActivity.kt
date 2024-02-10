package com.smadu1.temperatureapp.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smadu1.temperatureapp.R
import com.smadu1.temperatureapp.databinding.ActivityMainBinding
import com.smadu1.temperatureapp.utils.Config.NAME_FILE_SHARED_PREFERENCE
import com.smadu1.temperatureapp.utils.Config.VALUE_PREF_FULL_NAME

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        val pref = getSharedPreferences(NAME_FILE_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val name = pref.getString(VALUE_PREF_FULL_NAME, getString(R.string.nama_lengkap))
        binding.tvFullName.text = "Hi $name! semangat yaah!"

        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.animatedBottomBar.setupWithViewPager2(binding.viewPager)
        binding.animatedBottomBar.apply {
            onTabSelected = {}
            onTabReselected = {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}