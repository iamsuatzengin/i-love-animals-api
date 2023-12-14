package com.suatzengin.routes.veterinaryclinic

import com.suatzengin.data.dao.veterinaryclinic.VeterinaryClinicDao
import com.suatzengin.data.request.veterinaryclinic.VeterinaryClinicRequest
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addVeterinaryClinic(dao: VeterinaryClinicDao) {
    authenticate {
        post("/clinics") {
            runCatching {
                val requestBody = call.receive<VeterinaryClinicRequest>()

                dao.addVeterinaryClinic(veterinaryClinicRequest = requestBody)

                call.respond(
                    status = HttpStatusCode.Created,
                    message = MessageResponse(
                        message = "Başarılı bir şekilde eklendi!",
                        status = true
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