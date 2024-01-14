package com.suatzengin.routes.adcomments

import com.suatzengin.data.PushNotificationService
import com.suatzengin.data.dao.adcomment.AdCommentDao
import com.suatzengin.data.dao.advertisement.AdvertisementDao
import com.suatzengin.data.request.adcomment.AdvertisementCommentRequest
import com.suatzengin.data.response.MessageResponse
import com.suatzengin.util.exception.AuthenticationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import java.util.*

fun Route.addAdvertisementComment(
    dao: AdCommentDao,
    advertisementDao: AdvertisementDao,
    pushNotificationService: PushNotificationService
) {
    authenticate {
        post("/advertisement/{id}/comment") {
            runCatching {
                val id = call.parameters["id"]
                val uuid = UUID.fromString(id)

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                    ?: throw AuthenticationException(errorMessage = "Kullanıcı bulunamadı!")

                val requestBody = call.receive<AdvertisementCommentRequest>()

                val advertisement = async { advertisementDao.getAdvertisementById(uuid) }

                dao.addAdvertisementComment(
                    advertisementId = uuid,
                    userId = UUID.fromString(userId),
                    commentRequest = requestBody
                )

                pushNotificationService.sendMessage(
                    advertisement.await().creatorId.toString(),
                    "Yeni bir yorum var!",
                    "Paylaşmış olduğun ilanına bir yorum eklendi."
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