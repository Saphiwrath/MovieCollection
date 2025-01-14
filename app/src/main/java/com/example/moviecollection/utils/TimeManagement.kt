package com.example.moviecollection.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getDaysOfWeek(): Array<String> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val lastWeekDates: Array<String> = Array(7){""}

    for (i in 0..6) {
        lastWeekDates[i] = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, -1)
    }

    return lastWeekDates.reversedArray()
}
