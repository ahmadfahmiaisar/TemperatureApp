package com.temperatureapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent


fun Activity.startActivityWithAnimation(intent: Intent) {
    val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in, R.anim.slide_out)
    startActivity(intent, options.toBundle())
}