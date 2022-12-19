package ru.nsu.poc2.repositories

import ru.nsu.poc2.network.Api
import ru.nsu.poc2.network.json.registration.RegistrationRequest

class RegistrationRepository {
    suspend fun registerUser(registrationRequest: RegistrationRequest){
        Api.apiService.register(registrationRequest)
    }
}