package com.suatzengin.routes.adcomments

import com.suatzengin.data.dao.adcomment.AdCommentDao
import com.suatzengin.data.request.adcomment.AdvertisementCommentRequest
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateAdvertisementComment(dao: AdCommentDao) {
    authenticate {
        put("/comment/{commentId}") {
            runCatching {
                val commentId = call.parameters["commentId"]?.toLong()
                val requestBody = call.receive<AdvertisementCommentRequest>()

                val isUpdated = dao.updateAdvertisementComment(
                    commentId = commentId ?: 0,
                    commentRequest = requestBody
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