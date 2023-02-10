package ru.nsu.poc2.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.nsu.poc2.database.dao.LoginDAO
import ru.nsu.poc2.database.dbentities.LoginEntity
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.network.json.login.LoginRequest
import ru.nsu.poc2.network.json.login.LoginResponse
import ru.nsu.poc2.repositories.LoginRepository
import ru.nsu.poc2.utils.LogTags

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

    fun autoLogin(){
        viewModelScope.launch {
            _status.value = StatusValue.LOADING
            try {
                Log.d(LogTags.AUTOLOGIN, "loading")
                val loginEntity = findUserInDatabase()
                if (loginEntity == null){
                    Log.e(LogTags.AUTOLOGIN, "error no such user")
                    _status.value = StatusValue.ERROR
                    return@launch
                }
                val email = loginEntity.email
                val password = loginEntity.password
                _response.value = repository.auth(LoginRequest(email, password))
                _status.value = StatusValue.SUCCESS
                Log.d(LogTags.AUTOLOGIN, "success")
                parseJWT(_response)
            } catch (e: Exception){
                Log.e(LogTags.AUTOLOGIN, "error $e")
                _status.value = StatusValue.ERROR
            }
        }
    }

    private suspend fun addToBd(email: String, password: String) {
        repository.addUser(LoginEntity(0, email, password))
        Log.d(LogTags.LOGIN, "${repository.getUser()}")
    }

    private fun parseJWT(response: LiveData<LoginResponse>) {
        Log.d(LogTags.LOGIN, "${response.value}")
        val sharedPreferences = Application().getSharedPreferences("jwtPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", response.value!!.token)
        editor.apply()
    }

    private suspend fun findUserInDatabase() : LoginEntity?{
        return repository.getUser()
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
