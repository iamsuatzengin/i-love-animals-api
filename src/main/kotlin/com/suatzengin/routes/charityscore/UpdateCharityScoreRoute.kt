package com.suatzengin.routes.charityscore

import com.suatzengin.data.dao.charityscore.CharityScoreDao
import com.suatzengin.data.request.charityscore.UpdateScoreRequest
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateCharityScore(charityScoreDao: CharityScoreDao) {
    authenticate {
        put("/charity-score") {

            runCatching {
                val requestBody = call.receive<UpdateScoreRequest>()
                val isUpdated = charityScoreDao.updateUserCharityScore(request = requestBody)

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
