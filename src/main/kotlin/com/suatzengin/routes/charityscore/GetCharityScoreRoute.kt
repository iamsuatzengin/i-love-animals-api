package com.suatzengin.routes.charityscore

import com.suatzengin.data.dao.charityscore.CharityScoreDao
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getCharityScores(charityScoreDao: CharityScoreDao) {
    authenticate {
        get("/charity-score") {
            runCatching {
                val response = charityScoreDao.getAllCharityScore()

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
