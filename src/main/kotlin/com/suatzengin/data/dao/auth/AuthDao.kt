package com.suatzengin.data.dao.auth

import com.suatzengin.data.request.auth.RegisterRequest
import com.suatzengin.model.User
import java.util.UUID

interface AuthDao {

    suspend fun register(registerRequest: RegisterRequest): UUID

    suspend fun findUserByEmail(email: String): User?
}
