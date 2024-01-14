package com.suatzengin.data.dao.pushnotification

import com.suatzengin.data.dao.Dao
import com.suatzengin.model.PushNotificationDevice

interface PushNotificationDao: Dao<PushNotificationDevice> {

    suspend fun getPushNotificationDevices(userId: String): List<PushNotificationDevice>

    suspend fun createPushNotificationDevice(
        userId: String,
        deviceToken: String
    )
}
