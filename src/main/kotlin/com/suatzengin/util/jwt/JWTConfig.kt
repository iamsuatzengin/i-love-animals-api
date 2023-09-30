package com.suatzengin.util.jwt

data class JWTConfig(
    val audience: String,
    val issuer: String,
    val secret: String,
    val realm: String,
)
