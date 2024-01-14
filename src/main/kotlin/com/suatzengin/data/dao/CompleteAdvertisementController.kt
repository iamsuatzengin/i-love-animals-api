package com.suatzengin.data.dao

import com.suatzengin.data.PushNotificationService
import com.suatzengin.data.dao.advertisement.AdvertisementDao
import com.suatzengin.data.dao.charityscore.CharityScoreDao
import com.suatzengin.data.request.charityscore.UpdateScoreRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.util.*

class CompleteAdvertisementController(
    private val advertisementDao: AdvertisementDao,
    private val charityScoreDao: CharityScoreDao,
    private val pushNotificationService: PushNotificationService
) {

    suspend fun completeAdvertisement(
        userId: String,
        request: CompleteAdvertisementRequest
    ): Boolean = withContext(Dispatchers.IO) {
        runCatching {
            val updateAdvertisementDeferred = async {
                advertisementDao.updateIsCompletedAdvertisement(request.advertisementId.toUUID(), true)
            }

            val updateScoreDeferred = async {
                charityScoreDao.updateUserCharityScore(UpdateScoreRequest(userId = userId, point = 5))
            }

            val isCompleted = updateAdvertisementDeferred.await() && updateScoreDeferred.await()

            if(isCompleted) {
                sendPushNotification(request.advertisementId)
            }

            isCompleted
        }.getOrElse { false }
    }

    private suspend fun sendPushNotification(advertisementId: String) = withContext(Dispatchers.IO) {
        val advertisement = async {
            advertisementDao.getAdvertisementById(UUID.fromString(advertisementId))
        }

        val advertisementCreatorId = advertisement.await().creatorId.toString()

        pushNotificationService.sendMessage(
            userId = advertisementCreatorId,
            title = "İlanına yardım ulaştı!",
            body = "İlanına bir kullanıcı tarafından yardım ulaştırıldı."
        )
    }

    private fun String.toUUID() = UUID.fromString(this)
}

@Serializable
data class CompleteAdvertisementRequest(
    val advertisementId: String,
    val creatorId: String
)
