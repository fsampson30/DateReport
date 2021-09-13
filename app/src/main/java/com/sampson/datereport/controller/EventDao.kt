package com.sampson.datereport.controller

import androidx.room.*
import com.sampson.datereport.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event_table ORDER BY inicialDate")
    fun getAllEvents(): kotlinx.coroutines.flow.Flow<MutableList<Event>>

    @Query("SELECT * FROM event_table WHERE CAST(JulianDay('now') - JulianDay(inicialDate) as INTEGER) > 3")
    fun getOnTimeEvents(): kotlinx.coroutines.flow.Flow<MutableList<Event>>

    @Query("SELECT * FROM event_table WHERE JulianDay('now') - JulianDay(inicialDate) < 2")
    fun getWarningEvents(): kotlinx.coroutines.flow.Flow<MutableList<Event>>

    @Query("SELECT * FROM event_table WHERE CAST(JulianDay('now') - JulianDay(inicialDate) as INTEGER) <= 0")
    fun getExpiredEvents(): kotlinx.coroutines.flow.Flow<MutableList<Event>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event)

    @Query("DELETE FROM event_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteEvent(event: Event)
}