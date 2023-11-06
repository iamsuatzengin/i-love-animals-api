package com.suatzengin

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.suatzengin.di.appModule
import com.suatzengin.plugins.*
import com.suatzengin.util.extensions.getPropertyEnv
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.defaultheaders.*
import org.koin.ktor.plugin.Koin
import java.io.FileInputStream

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        modules(appModule)
    }

    install(DefaultHeaders) {
        header("Authorization", "Bearer ${this@module.getAccessToken()}")
        header("Content-Type", "application/json; UTF-8")
    }

    val serviceAccount = FileInputStream(
        getPropertyEnv("ktor.serviceAccountKey.path")
    )
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    FirebaseApp.initializeApp(options)

    println("access token : ${getAccessToken()}")

    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureRouting()
}

fun Application.getAccessToken(): String = runCatching {
    val messagingScope = "https://www.googleapis.com/auth/firebase.messaging"
    val googleCredentials = GoogleCredentials
        .fromStream(FileInputStream(getPropertyEnv("ktor.serviceAccountKey.path")))
        .createScoped(messagingScope)

    googleCredentials.refresh()
    googleCredentials.accessToken.tokenValue
}.getOrElse {
    ""
}

fun sendMessage() {
    val token = "TOKEN"

    val message = Message.builder()
        .setNotification(
            Notification.builder()
                .setTitle("Message from backend")
                .setBody("This message received from the server!")
                .build()
        )
        .putData("deeplink", "uri")
        .setToken(token)
        .build()

    val response = FirebaseMessaging.getInstance().send(message)

    println("response: $response")
}