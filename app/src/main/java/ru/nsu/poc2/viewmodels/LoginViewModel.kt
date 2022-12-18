package ru.nsu.poc2.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.nsu.poc2.PocApplication
import ru.nsu.poc2.database.AppDataBase
import ru.nsu.poc2.database.dao.LoginDAO
import ru.nsu.poc2.database.dbentities.LoginEntity
import ru.nsu.poc2.network.Api
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.network.json.login.LoginRequest
import ru.nsu.poc2.network.json.login.LoginResponse
import ru.nsu.poc2.repositories.LoginRepository
import ru.nsu.poc2.utils.LogTags
import java.security.MessageDigest
import java.util.concurrent.Flow
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class LoginViewModel(private val loginDAO: LoginDAO): ViewModel() {
    private val _status = MutableLiveData<StatusValue>()
    private val _response = MutableLiveData<LoginResponse>()
    val status: LiveData<StatusValue> = _status
    val response: LiveData<LoginResponse> = _response

    private val repository: LoginRepository = LoginRepository(loginDAO)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _status.value = StatusValue.LOADING
            try {
                _response.value = repository.auth(LoginRequest(email, password))
                Log.d(LogTags.LOGIN, "Current thread is ${Thread.currentThread().name}")
                _status.value = StatusValue.SUCCESS
                Log.d(LogTags.LOGIN, "Successfully logged in")
                Log.d(LogTags.LOGIN, "Successfully added record to the db")
                parseJWT(_response)
            } catch (e: Exception){
                _status.value = StatusValue.ERROR
                Log.d(LogTags.LOGIN, "Error while logging in $e")
            }
            if(_status.value == StatusValue.SUCCESS){
                addToBd(email, password)
            }
        }
    }

    private suspend fun addToBd(email: String, password: String) {
        repository.addUser(LoginEntity(0, email, password))
        Log.d(LogTags.LOGIN, "${repository.getUsers()[0]}")
    }

    private fun parseJWT(response: LiveData<LoginResponse>) {
        Log.d(LogTags.LOGIN, "${response.value}")
        val sharedPreferences = Application().getSharedPreferences("jwtPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", response.value!!.token)
        editor.apply()
    }
}
class LoginViewModelFactory(private val loginDAO: LoginDAO) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginDAO) as T
        }
        throw NullPointerException("No such class")
    }
}
