package com.suatzengin.routes.pushnotification

import com.suatzengin.data.dao.pushnotification.PushNotificationDao
import com.suatzengin.data.response.MessageResponse
import com.suatzengin.util.exception.AuthenticationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createPushNotificationDevice(
    dao: PushNotificationDao
) {
    authenticate {
        post("/push-notification-device/{deviceToken}") {
            runCatching {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                    ?: throw AuthenticationException(errorMessage = "Kullanıcı bulunamadı!")

                val deviceToken = call.parameters["deviceToken"].orEmpty()

                dao.createPushNotificationDevice(
                    userId = userId,
                    deviceToken = deviceToken
                )

                call.respond(
                    status = HttpStatusCode.Created,
                    message = MessageResponse(
                        message = "Başarılı bir şekilde eklendi!",
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
}
