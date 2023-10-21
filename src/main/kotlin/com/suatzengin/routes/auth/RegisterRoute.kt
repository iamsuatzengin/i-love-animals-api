package com.suatzengin.routes.auth

import com.suatzengin.data.dao.auth.AuthDao
import com.suatzengin.data.dao.profile.ProfileDao
import com.suatzengin.data.request.auth.RegisterRequest
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async

fun Route.registerRoute(
    authDao: AuthDao,
    profileDao: ProfileDao
) {
    post("register") {
        val requestBody = call.receive<RegisterRequest>()

        runCatching {
            val createdUserId = async { authDao.register(registerRequest = requestBody) }
            profileDao.createUserProfile(userId = createdUserId.await())

            call.respond(
                status = HttpStatusCode.Created,
                message = MessageResponse(
                    message = "Başarılı bir şekilde kayıt olundu!",
                    status = true
                )
            )

        }.onFailure {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = MessageResponse(
                    message = it.message ?: "Bir hatayla karşılaşıldı",
                    status = false
                )
            )
        }
    }
}
