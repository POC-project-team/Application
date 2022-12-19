package ru.nsu.poc2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.network.json.registration.RegistrationRequest
import ru.nsu.poc2.repositories.RegistrationRepository

class RegistrationViewModel: ViewModel() {
    private val _status: MutableLiveData<StatusValue> = MutableLiveData()
    val status: LiveData<StatusValue> = _status
    private val registrationRepository = RegistrationRepository()

    fun registerUser(email: String, password: String){
        viewModelScope.launch {
            _status.value = StatusValue.LOADING
            try {
                registrationRepository.registerUser(RegistrationRequest(email, password))
                _status.value = StatusValue.SUCCESS
            } catch (e: Exception){
                _status.value = StatusValue.ERROR
            }
        }
    }
}