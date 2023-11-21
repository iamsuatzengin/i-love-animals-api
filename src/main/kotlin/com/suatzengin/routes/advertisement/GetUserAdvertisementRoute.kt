package com.suatzengin.routes.advertisement

import com.suatzengin.data.dao.advertisement.AdvertisementDao
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.getUserAdvertisement(dao: AdvertisementDao) {
    authenticate {
        get("/user-advertisement/{userId}") {
            runCatching {
                val id = call.parameters["userId"]
                val uuid = UUID.fromString(id)
                val response = dao.getUserAdvertisement(uuid).map { advertisement ->
                    advertisement.toResponseModel()
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