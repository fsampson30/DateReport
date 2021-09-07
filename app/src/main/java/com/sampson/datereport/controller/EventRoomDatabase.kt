package com.sampson.datereport.controller

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sampson.datereport.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Event::class), version = 1, exportSchema = false)
abstract class EventRoomDatabase : RoomDatabase() {

    abstract fun EventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): EventRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    com.sampson.datereport.controller.EventRoomDatabase::class.java,
                    "event_database"
                ).addCallback(EventDatabaseCallback(scope))
                    .build()
                com.sampson.datereport.controller.EventRoomDatabase.Companion.INSTANCE = instance
                instance
            }
        }
    }

    private class EventDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.EventDao())
                }
            }
        }

        suspend fun populateDatabase(eventDao: EventDao) {
            eventDao.deleteAll()
            val event = Event(0, "Testing", "07/09/2021", 7, "14/09/2021", "", true, true)
            eventDao.insert(event)
        }
    }
}

