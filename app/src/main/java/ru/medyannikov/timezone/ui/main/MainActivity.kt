package ru.medyannikov.timezone.ui.main

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Spinner
import org.jetbrains.anko.alarmManager
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import ru.medyannikov.timezone.R
import ru.medyannikov.timezone.service.BootReceiver.Companion.TIME_ZONE
import ru.medyannikov.timezone.ui.base.AdapterTimeZone
import tv.mosobr.ui.base.BaseActivity
import java.util.*


class MainActivity : BaseActivity() {

  val spinner: Spinner by lazy { find<Spinner>(R.id.spinner_time_zone_1) }
  val button: Button by lazy { find<Button>(R.id.buttonOk_1) }

  override fun getLayout() = R.layout.a_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initAdapterTimeZone(getTimeZoneList())
    initViews()
  }

  private fun initViews() {
    button.onClick { onClickConfirmZone() }
  }

  private fun getTimeZoneList(): List<Pair<String, String>> {
    val timezones = TimeZone.getAvailableIDs()
    return timezones.map {
      Pair(TimeZone.getTimeZone(it).getDisplayName(false, TimeZone.SHORT, Locale.getDefault()), it)
    }.sortedBy { it.first }
  }

  private fun initAdapterTimeZone(list: List<Pair<String, String>>) {
    val adapterTimeZone = AdapterTimeZone(this, list)
    spinner.adapter = adapterTimeZone
    val defaultTimeZone = defaultSharedPreferences.getString(TIME_ZONE, null) ?: TimeZone.getDefault().id
    val pos = adapterTimeZone.getPosition(defaultTimeZone)
    spinner.setSelection(pos)
    spinner.gravity = Gravity.FILL
  }

  fun onClickConfirmZone() {
    val timeZoneId = (spinner.selectedItem as Pair<String, String>).second

    alarmManager.setTimeZone(timeZoneId)
    defaultSharedPreferences.edit().putString(TIME_ZONE, timeZoneId).apply()
  }
}
