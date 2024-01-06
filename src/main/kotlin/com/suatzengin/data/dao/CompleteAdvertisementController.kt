package com.suatzengin.data.dao

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
    private val charityScoreDao: CharityScoreDao
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

            updateAdvertisementDeferred.await() && updateScoreDeferred.await()
        }.getOrElse { false }

    }

    private fun String.toUUID() = UUID.fromString(this)
}

@Serializable
data class CompleteAdvertisementRequest(
    val advertisementId: String,
    val creatorId: String
)
