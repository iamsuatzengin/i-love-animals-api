package com.suatzengin

import com.suatzengin.di.appModule
import com.suatzengin.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        modules(appModule)
    }

    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureRouting()
    configureFirebaseAdmin()
}
