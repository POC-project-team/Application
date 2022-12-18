package ru.nsu.poc2

import android.app.Application
import ru.nsu.poc2.database.AppDataBase

class PocApplication : Application() {
    val database: AppDataBase by lazy {
        AppDataBase.getDatabase(this)
    }
}