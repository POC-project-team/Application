package ru.nsu.poc2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nsu.poc2.database.dao.LoginDAO
import ru.nsu.poc2.database.dbentities.LoginEntity

@Database(entities = [LoginEntity::class], version = 1)

abstract class AppDataBase : RoomDatabase() {
    abstract fun loginDao(): LoginDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
                        .createFromAsset("database/app.db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}