package com.suatzengin.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.suatzengin.util.extensions.EMPTY_STRING
import com.suatzengin.util.extensions.getPropertyEnv
import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*
import java.io.FileInputStream

private const val MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging"

fun Application.configureFirebaseAdmin() {
    install(DefaultHeaders) {
        header("Authorization", "Bearer ${this@configureFirebaseAdmin.firebaseAccessToken}")
        header("Content-Type", "application/json; UTF-8")
    }

    val serviceAccount = FileInputStream(getPropertyEnv("ktor.serviceAccountKey.path"))

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    FirebaseApp.initializeApp(options)
}

val Application.firebaseAccessToken: String
    get() {
        return runCatching {
            val googleCredentials = GoogleCredentials
                .fromStream(FileInputStream(this.getPropertyEnv("ktor.serviceAccountKey.path")))
                .createScoped(MESSAGING_SCOPE)

            googleCredentials.refresh()
            googleCredentials.accessToken.tokenValue
        }.getOrElse { EMPTY_STRING }
    }
