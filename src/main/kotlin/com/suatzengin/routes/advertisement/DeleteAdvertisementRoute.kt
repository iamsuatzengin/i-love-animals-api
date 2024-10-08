package com.suatzengin.routes.advertisement

import com.suatzengin.data.dao.advertisement.AdvertisementDao
import com.suatzengin.data.response.MessageResponse
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