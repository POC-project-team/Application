package ru.nsu.poc2.repositories

import androidx.lifecycle.LiveData
import ru.nsu.poc2.database.dao.LoginDAO
import ru.nsu.poc2.database.dbentities.LoginEntity
import ru.nsu.poc2.network.Api
import ru.nsu.poc2.network.json.login.LoginRequest
import ru.nsu.poc2.network.json.login.LoginResponse

class LoginRepository(private val loginDAO: LoginDAO) {
    suspend fun addUser(loginEntity: LoginEntity){
        loginDAO.insertAccToDB(loginEntity)
    }

    suspend fun getUsers(): List<LoginEntity>{
        return loginDAO.getAccounts()
    }

    suspend fun auth(loginRequest: LoginRequest): LoginResponse{
        return Api.apiService.auth(loginRequest)
    }
}