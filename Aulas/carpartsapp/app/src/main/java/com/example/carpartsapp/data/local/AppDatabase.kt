package com.example.carpartsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.carpartsapp.data.local.converter.DateConverter
import com.example.carpartsapp.data.local.converter.ListConverter
import com.example.carpartsapp.data.local.dao.CarDao
import com.example.carpartsapp.data.local.dao.CarShareDao
import com.example.carpartsapp.data.local.dao.CarPartDao
import com.example.carpartsapp.data.local.entity.CarEntity
import com.example.carpartsapp.data.local.entity.CarShareEntity
import com.example.carpartsapp.data.local.entity.CarPartEntity

@Database(
    entities = [
        CarEntity::class,
        CarShareEntity::class,
        CarPartEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    // DAOs
    abstract fun carDao(): CarDao
    abstract fun carShareDao(): CarShareDao
    abstract fun carPartDao(): CarPartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "carparts_database"
                )
                    .fallbackToDestructiveMigration()
                    // .createFromAsset("database/initial_data.db")
                    // .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}