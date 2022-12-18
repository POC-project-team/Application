package ru.nsu.poc2.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nsu.poc2.network.Api
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.network.json.login.LoginRequest
import ru.nsu.poc2.network.json.login.LoginResponse
import ru.nsu.poc2.utils.LogTags

class LoginViewModel: ViewModel() {
    private val _status = MutableLiveData<StatusValue>()
    private val _response = MutableLiveData<LoginResponse>()
    val status: LiveData<StatusValue> = _status
    val response: LiveData<LoginResponse> = _response

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _status.value = StatusValue.LOADING
            try {
                _response.value = Api.apiService.auth(LoginRequest(email, password))
                _status.value = StatusValue.SUCCESS
                storeAccount(email, password)
                Log.d(LogTags.LOGIN, "Successfully logged in")
            } catch (e: Exception){
                _status.value = StatusValue.ERROR
                Log.d(LogTags.LOGIN, "Error while logging in $e")
            }
        }
    }

    private fun storeAccount(email: String, password: String) {

    }
}