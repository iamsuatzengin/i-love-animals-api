package com.suatzengin.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.suatzengin.data.response.MessageResponse
import com.suatzengin.util.extensions.configureJWTConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    val jwtConfig = configureJWTConfig()

    authentication {
        jwt {
            realm = jwtConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtConfig.secret))
                    .withAudience(jwtConfig.audience)
                    .withIssuer(jwtConfig.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("userId").asString() != null) JWTPrincipal(credential.payload)
                else null
            }

            challenge { defaultScheme, realm ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    MessageResponse(
                        message = "Token is not valid or has expired",
                        status = false
                    )
                )
            }
        }
    }
}
