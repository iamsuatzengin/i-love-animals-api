package com.suatzengin.plugins

import com.suatzengin.data.advertisement.AdvertisementDao
import com.suatzengin.data.auth.AuthDao
import com.suatzengin.routes.advertisement.addAdvertisement
import com.suatzengin.routes.advertisement.getAllAdvertisementRoute
import com.suatzengin.routes.auth.loginRoute
import com.suatzengin.routes.auth.registerRoute
import com.suatzengin.util.extensions.configureJWTConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val authDao by inject<AuthDao>()
    val advertisementDao by inject<AdvertisementDao>()
    val jwtConfig = configureJWTConfig()

    routing {

        // Auth routes
        registerRoute(dao = authDao)
        loginRoute(dao = authDao, jwtConfig = jwtConfig)

        // Advertisement routes
        getAllAdvertisementRoute(dao = advertisementDao)
        addAdvertisement(dao = advertisementDao)
    }
}
