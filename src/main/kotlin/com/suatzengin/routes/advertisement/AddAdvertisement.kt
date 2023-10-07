package com.suatzengin.routes.advertisement

import com.suatzengin.data.advertisement.AdvertisementDao
import com.suatzengin.data.request.AdvertisementRequest
import com.suatzengin.data.response.PostResponse
import com.suatzengin.util.exception.AuthenticationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addAdvertisement(dao: AdvertisementDao) {
    authenticate {
        post("/add-advertisement") {
            runCatching {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                    ?: throw AuthenticationException(errorMessage = "Kullanıcı bulunamadı!")

                val requestBody = call.receive<AdvertisementRequest>()

                dao.addAdvertisement(
                    advertisementRequest = requestBody,
                    userId = userId
                )

                call.respond(
                    status = HttpStatusCode.Created, message =
                    PostResponse(
                        message = "Başarılı bir şekilde eklendi!",
                        status = true
                    )
                )
            }.onFailure {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = PostResponse(
                        message = it.message ?: "Bir hatayla karşılaşıldı",
                        status = true
                    )
                )
            }
        }
    }

}