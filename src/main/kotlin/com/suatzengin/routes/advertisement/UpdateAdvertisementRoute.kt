package com.suatzengin.routes.advertisement

import com.suatzengin.data.dao.advertisement.AdvertisementDao
import com.suatzengin.data.request.advertisement.UpdateAdRequest
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.updateAdvertisement(dao: AdvertisementDao) {
    authenticate {
        put("/advertisement/{id}") {
            runCatching {
                val requestBody = call.receive<UpdateAdRequest>()
                val id = call.parameters["id"]

                val isUpdated = dao.updateAdvertisement(
                    id = UUID.fromString(id),
                    updateAdRequest = requestBody
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