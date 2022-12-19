package ru.nsu.poc2.network.json.registration

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)