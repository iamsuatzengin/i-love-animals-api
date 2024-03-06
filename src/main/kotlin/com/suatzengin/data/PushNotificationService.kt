package com.suatzengin.data

import com.google.firebase.messaging.*
import com.suatzengin.data.dao.pushnotification.PushNotificationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PushNotificationService(
    private val pushNotificationDao: PushNotificationDao
) {

    suspend fun sendMessage(
        userId: String,
        title: String,
        body: String
    ) = withContext(Dispatchers.IO) {

        runCatching {
            val devices = pushNotificationDao.getPushNotificationDevices(userId)

            devices.forEach { device ->
                val message = Message.builder()
                    .setNotification(
                        Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build()
                    )
                    .setToken(device.deviceToken)
                    .build()

                val response = FirebaseMessaging.getInstance().send(message)
                println("response: $response")
            }
        }.onFailure { exception ->
            handleUnregisteredException(exception) {
                pushNotificationDao.deletePushNotificationDevice(userId)
            }
        }
    }

    private inline fun handleUnregisteredException(
        exception: Throwable,
        body: () -> Unit
    ) {
        if (exception is FirebaseMessagingException) {
            if (exception.messagingErrorCode == MessagingErrorCode.UNREGISTERED) {
                body()
            }
        }
    }
}
