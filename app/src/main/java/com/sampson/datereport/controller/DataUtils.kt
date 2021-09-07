package com.sampson.datereport.controller

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

val simpleDate = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
val todaysDate = simpleDate.format(Date())
val currentDate = Calendar.getInstance()

fun returnDayofWeek() : String {
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

fun returnTodaysDate() : String {
    return todaysDate
}