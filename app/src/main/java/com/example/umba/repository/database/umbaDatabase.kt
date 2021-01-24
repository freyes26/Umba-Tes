package com.example.umba.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.umba.R
import com.example.umba.repository.database.dao.movieDao
import com.example.umba.repository.database.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class umbaDatabase : RoomDatabase() {
    abstract fun movieDao(): movieDao

    companion object {
        @Volatile
        private var INSTANCE: umbaDatabase? = null

        fun getDatabase(context: Context): umbaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    umbaDatabase::class.java,
                    context.resources.getString(R.string.database_name)
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
