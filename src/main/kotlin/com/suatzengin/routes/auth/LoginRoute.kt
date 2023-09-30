package com.suatzengin.routes.auth

import com.suatzengin.data.auth.AuthDao
import com.suatzengin.data.request.LoginRequest
import com.suatzengin.data.response.LoginResponse
import com.suatzengin.util.exception.AuthenticationException
import com.suatzengin.util.extensions.generateToken
import com.suatzengin.util.jwt.JWTConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.loginRoute(dao: AuthDao, jwtConfig: JWTConfig) {
    post("/login") {

        val requestBody = call.receive<LoginRequest>()

        runCatching {
            val user = dao.findUserByEmail(requestBody.email)
                ?: throw AuthenticationException("Yanlış email veya şifre!")

            val isPasswordMatched = user.password == requestBody.password

            if(!isPasswordMatched) throw AuthenticationException("Girdiğiniz şifre yanlış. Tekrar Deneyiniz!")

            val token = generateToken(jwtConfig = jwtConfig, userId = user.id)

            call.respond(
                status = HttpStatusCode.OK,
                message = LoginResponse(
                    message = "Başarıyla giriş yapıldı!",
                    status = true,
                    token = token
                )
            )
        }.onFailure { throwable ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = LoginResponse(
                    message = throwable.message ?: "Giriş yaparken bir hata ile karşılaşıldı!",
                    status = false,
                    token = null
                )
            )
        }
    }
}
