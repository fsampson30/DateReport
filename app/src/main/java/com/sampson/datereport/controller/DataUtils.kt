package com.sampson.datereport.controller

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

val simpleDate = SimpleDateFormat("dd/MM/yyyy")
val todaysDate = simpleDate.format(Date())
val currentDate = Calendar.getInstance()

fun returnDayofWeek(): String {
    when (currentDate[Calendar.DAY_OF_WEEK]) {
        1 -> return "Sunday"
        2 -> return "Monday"
        3 -> return "Tuesday"
        4 -> return "Wednesday"
        5 -> return "Thursday"
        6 -> return "Friday"
        7 -> return "Saturday"
    }
    return ""
}

fun returnTodaysDateString(): String {
    return todaysDate
}

fun returnDaysToExpire(inicialDate: String): Long {
    val iDate = simpleDate.parse(inicialDate)
    Log.i("TAG",Calendar.getInstance().toString())
    return (Calendar.getInstance().timeInMillis - iDate.time) / (1000 * 60 * 60 * 24)
}

fun returnNotifyDate(daysToNotify: Int): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, daysToNotify)
    return simpleDate.format(calendar.time)
}

