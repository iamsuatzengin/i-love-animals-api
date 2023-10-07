package com.suatzengin.routes.advertisement

import com.suatzengin.data.advertisement.AdvertisementDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllAdvertisementRoute(dao: AdvertisementDao) {
    authenticate {
        get("/advertisement-list") {
            runCatching {
                val list = dao.getAdvertisement()

                val response = list.map { advertisement ->
                    advertisement.toResponseModel()
                }

                call.respond(message = response, status = HttpStatusCode.OK)
            }.onFailure {
                call.respond("Error -> ${it.localizedMessage}")
            }
        }
    }
}