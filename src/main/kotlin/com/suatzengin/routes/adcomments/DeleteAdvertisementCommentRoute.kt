package com.suatzengin.routes.adcomments

import com.suatzengin.data.adcomment.AdCommentDao
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteAdvertisementComment(dao: AdCommentDao) {
    authenticate {
        delete("/comment/{commentId}") {
            runCatching {
                val commentId = call.parameters["commentId"]?.toLong()

                val isDeleted = dao.deleteAdvertisementComment(commentId = commentId ?: 0)

                call.respond(
                    status = if (isDeleted) HttpStatusCode.OK else HttpStatusCode.BadRequest,
                    message = MessageResponse(
                        message = if (isDeleted) "Başarılı bir şekilde silindi." else "Silinirken hata meydana geldi!",
                        status = isDeleted
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