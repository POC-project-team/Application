package ru.nsu.poc2.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.nsu.poc2.database.dbentities.LoginEntity

@Dao
interface LoginDAO {
    @Query("SELECT * FROM LOGINENTITY")
    fun getAll(): List<LoginEntity>
    @Query("SELECT * FROM LOGINENTITY WHERE email = :email")
    fun getAccountsByLogin(email: String): LoginEntity
    @Insert
    fun insertAccToDB(loginEntity: LoginEntity)
    @Delete
    fun deleteAccFromDB(loginEntity: LoginEntity)
}