package com.dayker.airsearch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dayker.airsearch.database.dao.FlightDao
import com.dayker.airsearch.database.entity.Flight


@Database(
    entities = [Flight::class],
    version = 1,
    exportSchema = true
)

abstract class ProjectDatabase : RoomDatabase() {
    abstract fun flightDao(): FlightDao

    companion object {
        private var db_instance: ProjectDatabase? = null

        fun getAppDatabaseInstance(context: Context): ProjectDatabase {
            if (db_instance == null) {
                db_instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectDatabase::class.java,
                    "app_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return db_instance!!
        }
    }

}



