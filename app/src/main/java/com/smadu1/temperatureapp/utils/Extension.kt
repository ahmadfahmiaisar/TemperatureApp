package com.smadu1.temperatureapp.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import com.smadu1.temperatureapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Activity.startActivityWithAnimation(intent: Intent) {
    val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in, R.anim.slide_out)
    startActivity(intent, options.toBundle())
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    val currentDate = Date()
    return dateFormat.format(currentDate)
}
