package ru.medyannikov.timezone.ui.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.jetbrains.anko.layoutInflater
import ru.medyannikov.timezone.R
import java.util.*

class AdapterTimeZone(val context: Context, list: List<Pair<String, String>>) : BaseAdapter() {

  val items: MutableList<Pair<String, String>> = ArrayList()

  init {
    addItems(list)
  }

  override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {
    var view = convertView
    if (view == null) {
      view = context.layoutInflater.inflate(R.layout.i_item_time_zone, parent, false)
    }
    val title = view?.findViewById(R.id.title) as TextView
    val subTitle = view?.findViewById(R.id.sub_title) as TextView
    title.text = items[p0].second
    subTitle.text = items[p0].first
    return view !!
  }

  override fun getItem(p0: Int): Any = items[p0]

  override fun getItemId(p0: Int): Long = 0

  override fun getCount(): Int = items.size

  fun getPosition(id: String) = items.indexOfFirst { it.second == id }

  fun addItems(list: List<Pair<String, String>>) {
    items.clear()
    items.addAll(list)
    notifyDataSetChanged()
  }
}