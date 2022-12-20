package ru.nsu.poc2.network

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import ru.nsu.poc2.network.json.login.LoginRequest
import ru.nsu.poc2.network.json.login.LoginResponse
import ru.nsu.poc2.network.json.registration.RegistrationRequest
import ru.nsu.poc2.ui.RegistrationFragment

private const val BASE_URL = "http://192.168.31.98:60494"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {
    @POST("/auth")
    suspend fun auth(@Body loginRequest: LoginRequest): LoginResponse
    @POST("/signup")
    suspend fun register(@Body registrationRequest: RegistrationRequest)

}

object Api {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
