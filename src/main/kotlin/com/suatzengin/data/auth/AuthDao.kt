package com.suatzengin.data.auth

import com.suatzengin.data.request.auth.RegisterRequest
import com.suatzengin.model.User

interface AuthDao {

    suspend fun register(registerRequest: RegisterRequest)

    suspend fun findUserByEmail(email: String): User?
}
