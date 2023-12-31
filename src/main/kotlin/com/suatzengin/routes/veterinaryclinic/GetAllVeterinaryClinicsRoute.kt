package com.suatzengin.routes.veterinaryclinic

import com.suatzengin.data.dao.veterinaryclinic.VeterinaryClinicDao
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllVeterinaryClinics(dao: VeterinaryClinicDao) {
    authenticate {
        get("/clinics") {
            runCatching {
                val list = dao.getAllVeterinaryClinics()

                call.respond(
                    status = HttpStatusCode.OK,
                    message = list
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