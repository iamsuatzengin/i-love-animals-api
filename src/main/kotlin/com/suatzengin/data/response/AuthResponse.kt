package com.suatzengin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val message: String,
    val status: Boolean
)

@Serializable
data class LoginResponse(
    val message: String,
    val status: Boolean,
    val token: String?
)
