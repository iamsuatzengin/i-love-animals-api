package com.suatzengin.routes.adcomments

import com.suatzengin.data.adcomment.AdCommentDao
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.getAdvertisementComments(dao: AdCommentDao) {
    authenticate {
        get("/advertisement/{id}/comments") {
            runCatching {
                val advertisementId = call.parameters["id"]
                val uuid = UUID.fromString(advertisementId)

                val response = dao.getAdvertisementComments(advertisementId = uuid).map { adComment ->
                    adComment.toResponseModel()
                }

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