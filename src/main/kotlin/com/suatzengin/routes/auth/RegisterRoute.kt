package com.suatzengin.routes.auth

import com.suatzengin.data.dao.auth.AuthDao
import com.suatzengin.data.dao.charityscore.CharityScoreDao
import com.suatzengin.data.dao.profile.ProfileDao
import com.suatzengin.data.request.auth.RegisterRequest
import com.suatzengin.data.response.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async

fun Route.registerRoute(
    authDao: AuthDao,
    profileDao: ProfileDao,
    charityScoreDao: CharityScoreDao
) {
    post("register") {
        val requestBody = call.receive<RegisterRequest>()

        runCatching {
            val createdUserId = authDao.register(registerRequest = requestBody)

            val deferredCreateUserProfile = async { profileDao.createUserProfile(userId = createdUserId) }
            val deferredAddCharityScore = async { charityScoreDao.addUserCharityScoreTable(userId = createdUserId) }

            deferredCreateUserProfile.await()
            deferredAddCharityScore.await()

            call.respond(
                status = HttpStatusCode.Created,
                message = MessageResponse(
                    message = "Başarılı bir şekilde kayıt olundu!",
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
