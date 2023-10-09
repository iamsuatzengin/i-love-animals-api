package com.suatzengin.routes.advertisement

import com.suatzengin.data.advertisement.AdvertisementDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.deleteAdvertisement(dao: AdvertisementDao) {

    authenticate {
        delete("/advertisement/{id}"){
            runCatching {
                val id = call.parameters["id"]

                val isDeleted = dao.deleteAdvertisement(id = UUID.fromString(id))

                call.respond(
                    status = if (isDeleted) HttpStatusCode.OK else HttpStatusCode.BadRequest,
                    message = if (isDeleted) "Başarılı bir şekilde silindi." else "Silinirken hata meydana geldi!"
                )
            }
        }
    }
}