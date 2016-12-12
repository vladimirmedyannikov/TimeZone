package ru.medyannikov.timezone.ui.main

import android.os.Bundle
import android.view.Gravity
import android.widget.Spinner
import butterknife.BindView
import butterknife.OnClick
import org.jetbrains.anko.alarmManager
import org.jetbrains.anko.defaultSharedPreferences
import ru.medyannikov.timezone.R
import ru.medyannikov.timezone.service.BootReceiver.Companion.TIME_ZONE
import ru.medyannikov.timezone.ui.base.AdapterTimeZone
import tv.mosobr.ui.base.BaseActivity
import java.util.*


class MainActivity : BaseActivity() {

  @BindView(R.id.spinner_time_zone)
  lateinit var spinner: Spinner

  override fun getLayout() = R.layout.a_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initAdapterTimeZone(getTimeZoneList())
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

  @OnClick(R.id.buttonOk)
  fun onClickConfirmZone() {
    val timeZoneId = (spinner.selectedItem as Pair<String, String>).second

    alarmManager.setTimeZone(timeZoneId)
    defaultSharedPreferences.edit().putString(TIME_ZONE, timeZoneId).apply()
  }
}
