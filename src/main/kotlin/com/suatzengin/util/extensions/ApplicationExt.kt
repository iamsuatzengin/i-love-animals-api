package com.suatzengin.util.extensions

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.suatzengin.util.jwt.JWTConfig
import io.ktor.server.application.*
import java.util.UUID

fun Application.getPropertyEnv(path: String) =
    environment.config.property(path).getString()

fun Application.configureJWTConfig(): JWTConfig {

    val audience = getPropertyEnv("ktor.jwt.audience")
    val issuer = getPropertyEnv("ktor.jwt.issuer")
    val secret = getPropertyEnv("ktor.jwt.secret")
    val realm = getPropertyEnv("ktor.jwt.realm")

    return JWTConfig(audience, issuer, secret, realm)
}

fun generateToken(jwtConfig: JWTConfig, userId: UUID): String = JWT.create()
    .withAudience(jwtConfig.audience)
    .withIssuer(jwtConfig.issuer)
    .withClaim("userId", userId.toString())
    .sign(Algorithm.HMAC256(jwtConfig.secret))

