package com.suatzengin.routes.profile

import com.suatzengin.data.dao.profile.ProfileDao
import com.suatzengin.data.request.profile.UpdateProfileRequest
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.updateUserProfile(profileDao: ProfileDao) {
    authenticate {
        put("/user/{userId}") {
            runCatching {
                val userId = call.parameters["userId"] ?: throw Exception("Kullanıcı bulanamadı")
                val requestBody = call.receive<UpdateProfileRequest>()

                val isUpdated = profileDao.updateUserProfile(
                    userId = UUID.fromString(userId),
                    request = requestBody
                )

                call.respond(
                    status = if (isUpdated) HttpStatusCode.OK else HttpStatusCode.BadRequest,
                    message = MessageResponse(
                        message = if (isUpdated) "Başarılı bir şekilde güncellendi." else "Güncellenirken hata meydana geldi!",
                        status = isUpdated
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
}
