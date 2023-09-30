package com.suatzengin.data.auth

import com.suatzengin.data.request.RegisterRequest
import com.suatzengin.model.User

interface AuthDao {

    suspend fun register(registerRequest: RegisterRequest)

    suspend fun findUserByEmail(email: String): User?
}
