package com.example.msger.core.data.data_source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.msger.feature_chat_manage.data.data_source.local.db.ChatDao
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity

@Database(
    [ChatEntity::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private const val DATABASE_NAME = "msger-database"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room
                    .databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}