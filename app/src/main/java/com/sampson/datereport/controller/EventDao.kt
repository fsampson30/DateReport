package com.sampson.datereport.controller

import androidx.room.*
import com.sampson.datereport.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event_table ORDER BY inicialDate")
    fun getAllEvents(): kotlinx.coroutines.flow.Flow<MutableList<Event>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event)

    @Query("DELETE FROM event_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteEvent(event: Event)
}