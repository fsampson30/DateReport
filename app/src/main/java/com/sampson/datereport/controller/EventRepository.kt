package com.sampson.datereport.controller

import androidx.annotation.WorkerThread
import com.sampson.datereport.model.Event
import java.util.concurrent.Flow

class EventRepository(private val eventDao: EventDao) {

    val allEvents: kotlinx.coroutines.flow.Flow<MutableList<Event>> = eventDao.getAllEvents()
    val onTimeEvents : kotlinx.coroutines.flow.Flow<MutableList<Event>> = eventDao.getOnTimeEvents()
    val warningEvents : kotlinx.coroutines.flow.Flow<MutableList<Event>> = eventDao.getWarningEvents()
    val expiredEvents : kotlinx.coroutines.flow.Flow<MutableList<Event>> = eventDao.getExpiredEvents()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    suspend fun delete() {
        eventDao.deleteAll()
    }

    suspend fun deleteEvent(event: Event){
        eventDao.deleteEvent(event)
    }
}