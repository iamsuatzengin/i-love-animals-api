package com.suatzengin.routes.completeadvertisement

import com.suatzengin.data.dao.CompleteAdvertisementController
import com.suatzengin.data.dao.CompleteAdvertisementRequest
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.completeAdvertisementRoute(
    controller: CompleteAdvertisementController
) {
    authenticate {
        post("/complete-advertisement") {
            val requestBody = call.receive<CompleteAdvertisementRequest>()
            val jwtPrincipal = call.principal<JWTPrincipal>()

            val currentUserId = jwtPrincipal?.payload?.getClaim("userId")?.asString().orEmpty()

            val isCompleted = controller.completeAdvertisement(currentUserId, requestBody)

            call.respond(
                status = if (isCompleted) HttpStatusCode.OK else HttpStatusCode.BadRequest,
                message = MessageResponse(
                    message = if (isCompleted) "Başarılı bir şekilde güncellendi." else "Güncellenirken hata meydana geldi!",
                    status = isCompleted
                )
            )
        }
    }
}
