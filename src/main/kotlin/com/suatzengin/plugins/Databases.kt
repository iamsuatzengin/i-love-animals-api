package com.suatzengin.plugins

import com.suatzengin.model.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.ds.PGSimpleDataSource

fun Application.configureDatabases() {
    val user = environment.config.propertyOrNull("ktor.postgres.user")?.getString()
    val password = environment.config.propertyOrNull("ktor.postgres.password")?.getString()
    val databaseName = environment.config.propertyOrNull("ktor.postgres.databaseName")?.getString()

    val dataSource = PGSimpleDataSource().apply {
        this.user = user
        this.password = password
        this.databaseName = databaseName
    }

    val database = Database.connect(dataSource)

    transaction(database) {
        SchemaUtils.create(UserTable)
        SchemaUtils.create(AdvertisementTable)
        SchemaUtils.create(AdCommentTable)
        SchemaUtils.create(ProfileTable)
        SchemaUtils.create(CharityScoreTable)
        SchemaUtils.create(VeterinaryClinicTable)
        SchemaUtils.create(PushNotificationDeviceTable)
    }
}
