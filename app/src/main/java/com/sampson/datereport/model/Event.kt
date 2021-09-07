package com.sampson.datereport.model

data class Event(
    var title : String,
    var inicialDate : String,
    var finishDate : String,
    var isOpen : Boolean,
    var isNotifiable : Boolean
)