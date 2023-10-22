package com.suatzengin.routes.profile

import com.suatzengin.data.dao.profile.ProfileDao
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.getUserProfile(profileDao: ProfileDao) {
    authenticate {
        get("/user/{userId}") {
            runCatching {
                val userId = call.parameters["userId"] ?: throw Exception("Kullanıcı bulanamadı")
                val response = profileDao.getUserProfile(userId = UUID.fromString(userId))

                call.respond(
                    status = HttpStatusCode.OK,
                    message = response
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
}
