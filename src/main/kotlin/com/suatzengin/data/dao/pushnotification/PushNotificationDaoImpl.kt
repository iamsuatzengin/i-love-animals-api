package com.suatzengin.data.dao.pushnotification

import com.suatzengin.model.PushNotificationDevice
import com.suatzengin.model.PushNotificationDeviceTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class PushNotificationDaoImpl: PushNotificationDao {
    override fun resultRow(row: ResultRow) = PushNotificationDevice(
        id = row[PushNotificationDeviceTable.id],
        userId = UUID.fromString(row[PushNotificationDeviceTable.userId]),
        deviceToken = row[PushNotificationDeviceTable.deviceToken]
    )

    override suspend fun getPushNotificationDevices(userId: String): List<PushNotificationDevice> = dbQuery {
        PushNotificationDeviceTable.select { PushNotificationDeviceTable.userId eq userId }
            .map(::resultRow)
            .toList()
    }

    override suspend fun createPushNotificationDevice(
        userId: String,
        deviceToken: String
    ) = dbQuery {
        val insertStatement = PushNotificationDeviceTable.insert { insertStatement ->
            insertStatement[this.userId] = userId
            insertStatement[this.deviceToken] = deviceToken
        }
    }
}
