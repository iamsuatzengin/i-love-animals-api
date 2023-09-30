package com.suatzengin.routes.auth

import com.suatzengin.data.auth.AuthDao
import com.suatzengin.data.request.RegisterRequest
import com.suatzengin.data.response.RegisterResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.registerRoute(dao: AuthDao) {
    post("register") {
        val requestBody = call.receive<RegisterRequest>()

        runCatching {
            dao.register(registerRequest = requestBody)
            call.respond(
                status = HttpStatusCode.Created,
                message = RegisterResponse(
                    message = "Başarılı bir şekilde kayıt olundu!",
                    status = true
                )
            )
        }.onFailure {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = RegisterResponse(
                    message = it.message ?: "Bir hatayla karşılaşıldı",
                    status = true
                )
            )
        }
    }
}
