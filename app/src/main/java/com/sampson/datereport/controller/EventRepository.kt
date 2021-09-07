package com.sampson.datereport.controller

import androidx.annotation.WorkerThread
import com.sampson.datereport.model.Event
import java.util.concurrent.Flow

class EventRepository(private val eventDao: EventDao) {

    val allEvents: kotlinx.coroutines.flow.Flow<MutableList<Event>> = eventDao.getAllEvents()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    suspend fun delete() {
        eventDao.deleteAll()
    }
}