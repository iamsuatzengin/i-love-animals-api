package com.suatzengin.routes.advertisement

import com.suatzengin.data.dao.advertisement.AdvertisementDao
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllAdvertisement(dao: AdvertisementDao) {
    authenticate {
        get("/advertisement-list") {
            runCatching {
                val list = dao.getAdvertisement()

                val response = list.map { advertisement ->
                    advertisement.toResponseModel()
                }

                call.respond(message = response, status = HttpStatusCode.OK)
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