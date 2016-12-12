package ru.medyannikov.timezone.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.alarmManager
import org.jetbrains.anko.defaultSharedPreferences

class BootReceiver : BroadcastReceiver() {

  companion object {
    const val TIME_ZONE = "time_zone"
  }

  override fun onReceive(context: Context?, data: Intent?) {
    val timeZone = context?.defaultSharedPreferences?.getString(TIME_ZONE, null)
    timeZone?.let {
      context?.alarmManager?.setTimeZone(timeZone)
    }
  }
}