package com.suatzengin.model

import org.jetbrains.exposed.sql.Table
import java.util.UUID

data class PushNotificationDevice(
    val id: Int,
    val userId: UUID,
    val deviceToken: String
)

object PushNotificationDeviceTable : Table() {
    val id = integer(name = "id").autoIncrement()
    val userId = text(name = "user_id")
    val deviceToken = text(name = "device_token")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
