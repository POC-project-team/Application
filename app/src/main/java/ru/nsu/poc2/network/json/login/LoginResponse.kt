package ru.nsu.poc2.network.json.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("description") val description: String,
    @SerializedName("content") val content: String
)