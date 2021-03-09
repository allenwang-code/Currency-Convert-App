package com.allenwang.currency.util

import java.text.SimpleDateFormat
import java.util.*

class CalendarUtil {

    companion object {
        fun getTimeYesterday(): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val formatter = SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH)
            return formatter.format(calendar.time)
        }
    }
}