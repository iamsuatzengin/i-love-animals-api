package com.suatzengin.routes.advertisement

import com.suatzengin.data.advertisement.AdvertisementDao
import com.suatzengin.data.response.PostResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.getAdvertisement(dao: AdvertisementDao) {
    authenticate {
        get("/advertisement/{id}") {
            runCatching {
                val id = call.parameters["id"]
                val uuid = UUID.fromString(id)
                val response = dao.getAdvertisementById(uuid).toResponseModel()

                call.respond(
                    status = HttpStatusCode.OK,
                    message = response
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