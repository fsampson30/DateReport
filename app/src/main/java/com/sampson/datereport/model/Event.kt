package com.sampson.datereport.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "event_table")
class Event(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    var title : String = "",
    var inicialDate : String = "",
    var daysToNotify : Int = 0,
    var notifyDate : String = "",
    var finishDate : String = "",
    var isOpen : Boolean = false,
    var isNotifiable : Boolean = false
) : Serializable