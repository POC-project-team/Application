package ru.nsu.poc2.database.dao

import androidx.room.Dao
import androidx.room.Query
import ru.nsu.poc2.database.dbentities.LoginEntity

@Dao
interface LoginDAO {
    @Query("SELECT * FROM LOGINENTITY")
    fun getAll(): List<LoginEntity>
    @Query("SELECT * FROM LOGINENTITY WHERE email = :email")
    fun getAccountsByLogin(email: String): List<LoginEntity>
}