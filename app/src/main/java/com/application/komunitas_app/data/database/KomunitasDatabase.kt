package com.application.komunitas_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Komunitas::class],
    version = 1
)
abstract class KomunitasDatabase : RoomDatabase() {

    abstract fun communitiesDao(): KomunitasDao

    companion object {
        @Volatile
        private var INSTANCE: KomunitasDatabase? = null

        fun getInstance(context: Context): KomunitasDatabase {
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KomunitasDatabase::class.java,
                    "komunitas_db"
                ).build()
                INSTANCE = instance
                instance
            }
            return INSTANCE as KomunitasDatabase
        }
    }
}